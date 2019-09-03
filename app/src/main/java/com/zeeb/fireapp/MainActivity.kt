package com.zeeb.fireapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.zeeb.fireapp.screens.home.HomeFragment

class MainActivity : AppCompatActivity() {


    private lateinit var firebaseAnalytics: FirebaseAnalytics



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

    }


//    companion object {
//        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
//            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        }
//    }
}
