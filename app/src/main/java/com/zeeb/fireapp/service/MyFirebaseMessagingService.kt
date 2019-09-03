package com.zeeb.fireapp.service

import android.annotation.SuppressLint
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("Registered")
class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        if (p0!!.notification != null){
            val title = p0.notification!!.title
            val body = p0.notification!!.body


            NotificationHelper.displayNotification(applicationContext, title!!, body!!)
        }

    }
}