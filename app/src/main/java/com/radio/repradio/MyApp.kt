package com.radio.repradio

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.ktx.messaging

class MyApp : Application(){

    companion object{
        const val NOTIFICATION_CHANNEL_ID = "NotificationFCM"
    }

    override fun onCreate() {
        super.onCreate()
        Firebase.messaging.token.addOnCompleteListener{
            if (!it.isSuccessful){
                println("error de token")
                return@addOnCompleteListener
            }
            val token = it.result
            println("token: $token")
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID,
            "Notification",
            NotificationManager.IMPORTANCE_HIGH)
            channel.description="Notificacion recibida desde fcm"
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

        }



    }

}