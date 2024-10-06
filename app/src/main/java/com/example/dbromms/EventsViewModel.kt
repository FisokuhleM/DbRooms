package com.example.dbromms

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class EventsViewModel(application: Application): AndroidViewModel(application) {
   private var isSyncInProgress = false
    private val eventDao : EventDao
    //The other classes
    val allEvents : LiveData<List<Events>>
    val eventInsertionStatus = MutableLiveData<Boolean>()

    init {
        val database = EventDatabase.getDatabase(application)
        eventDao=database.eventDao()
        allEvents = eventDao.getAllEvents().asLiveData()
    }


    //Insert abstraction method
    fun insert(events: Events){
        viewModelScope.launch {
            try {
                if(NetworkUtils.isNetworkAvailable(getApplication()))
                {
                    events.isSynced = true
                    syncWithFirebase(events)
                }
                else{
                    events.isSynced = false
                }
                eventDao.insert(events)
                eventInsertionStatus.postValue(true)
            }catch (e: Exception){
                eventInsertionStatus.postValue(false)
            }
        }
    }

    fun syncEventsWithServer(firestore: FirebaseFirestore){
        if(NetworkUtils.isNetworkAvailable(getApplication()) && !isSyncInProgress){
            isSyncInProgress = true
            viewModelScope.launch {
                try {
                    val unsyncedEvents = eventDao.getUnsyncedEvents()
                    for (event in unsyncedEvents){
                        syncWithFirebase(event)
                    }
                }catch(e: Exception) {
                    Log.e("Sync Error", "Failed to sync events: ${e.message}")
                }finally {
                    isSyncInProgress = false
                }



            }
        }
    }
    //var name = MutableLiveData<String>()
   // var desc = MutableLiveData<String>()

private fun syncWithFirebase(events: Events){
    val firestore = FirebaseFirestore.getInstance()


    //Create the event map
    val eventMap = hashMapOf(
        "name" to events.name,
        "description" to events.description,
        "isSynced" to true //Set true if syncing to Firestore
    )
    //Check if event with the same name exists in Firestore already
    firestore.collection("events")
        .whereEqualTo("name",events.name)
        .whereEqualTo("description", events.description)
        .get()
        .addOnSuccessListener {
            documents ->
            if (!documents.isEmpty){
                val documentId = documents.documents[0].id
                firestore.collection("events")
                    .document(documentId)
                    .set(eventMap)
                    .addOnSuccessListener {
                        viewModelScope.launch {
                            eventDao.insert(events.copy(isSynced = true, firestoreId = documentId))

                        }
                    }
            }
            else {
                val documentReference = firestore.collection("events").document()
                documentReference.set(eventMap)
                    .addOnSuccessListener {
                        viewModelScope.launch {
                            eventDao.insert(events.copy(isSynced = true, firestoreId = documentReference.id))
                        }
                        Log.d("FirestoreSync",
                            "New event added to firestore with id: ${documentReference}")
                    }

            }
        }
        .addOnFailureListener{ e ->
            Log.e("FirestoreError", "Error checking Firestore: ${e.message}")
        }
}
}