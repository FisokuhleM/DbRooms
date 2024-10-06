package com.example.dbromms

import android.app.Notification
import com.google.firebase.messaging.FirebaseMessaging

/*class NotificationService {
    //Setup for push notifications
    fun sendPushNotification(token:String, title: String, body: String){
        val message = Message.builder()
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build()
            )
            .setToken(token)
            .build()

        //FCM -- Firebase Messaging
        FirebaseMessaging.getInstance().send(message)
    }

    fun notifyUsersAboutUpdate(token:String)
    {val title = "Database update"
        val body = "The database has been updated"
        sendPushNotification(token,title,body)
    }
}*/