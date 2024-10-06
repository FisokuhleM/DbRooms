package com.example.dbromms

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.dbromms.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    //binding
    lateinit var binding: ActivityMainBinding
    lateinit var eventsViewModel: EventsViewModel

    //new
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //new
        firestore = FirebaseFirestore.getInstance()

        setContentView(R.layout.activity_main)
        //binding for layouts
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventsViewModel = ViewModelProvider(this,).get(EventsViewModel::class.java)

        //new
        FirebaseApp.initializeApp(this)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(NetworkReceiver(), intentFilter)

        //the btn to bring up fragment
        binding.newTaskButton.setOnClickListener {
            EventFragment().show(supportFragmentManager, "newEventTag")

        }

        eventsViewModel.allEvents.observe(this) { events ->
            //Toast optional
        }
    }
        override fun onResume() {
            super.onResume()
            //Sync events with firestore when app resumes
            eventsViewModel.syncEventsWithServer(firestore)
        }
}



