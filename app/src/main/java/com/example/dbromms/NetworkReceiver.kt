package com.example.dbromms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider

class NetworkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (NetworkUtils.isNetworkAvailable(context)){
            //when the network comes back online -- sync it
            val title = "Network status"
            val message = "the database is now online"
            sendNotificationToUsers(title,message)
        }else{
            val title = "Network Status"
            val message = "the database is now online"
            sendNotificationToUsers(title,message)
        }
    }

    private fun sendNotificationToUsers(title: String, message: String){


    }
}