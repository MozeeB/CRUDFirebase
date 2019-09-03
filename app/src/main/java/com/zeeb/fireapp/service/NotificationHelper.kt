package com.zeeb.fireapp.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.zeeb.fireapp.MainActivity
import com.zeeb.fireapp.R
import com.zeeb.fireapp.screens.home.HomeFragment

object NotificationHelper {


    fun displayNotification(context : Context, title : String, body: String){
        val intent = Intent(context, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            100,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val mBuilder = NotificationCompat.Builder(context,
            HomeFragment.CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)


        val mNotificationMnjr = NotificationManagerCompat.from(context)
        mNotificationMnjr.notify(1, mBuilder.build())
    }
}