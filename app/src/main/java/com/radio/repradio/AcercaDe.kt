package com.radio.repradio

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import android.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import version

class AcercaDe : AppCompatActivity() {
    private val NOTIFICATION_PERMISSION_REQUEST = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_acerca_de)

        val politicas = findViewById<CardView>(R.id.politicas)
        politicas.setOnClickListener {
            val url = "https://www.app-privacy-policy.com/live.php?token=7uXiOtlMHwXaS5XSCMlOIbYjLxhF7v7E"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
            finish()
        }

        val licencias = findViewById<CardView>(R.id.licencias)
        licencias.setOnClickListener {
            val url = "https://www.app-privacy-policy.com/live.php?token=sybB9AQKBdCHTaZP75Gzzr3l2vmfXrAC"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
            finish()
        }

        val terminos = findViewById<CardView>(R.id.terminos)
        terminos.setOnClickListener {
            val url = "https://www.app-privacy-policy.com/live.php?token=XszIm6TDrn6yC2YaFDZ1BEk0mAmqQt3q"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
            finish()
        }

        val permisos = findViewById<CardView>(R.id.permisos)
        permisos.setOnClickListener {
            val notificationManager = NotificationManagerCompat.from(this)

            if (!notificationManager.areNotificationsEnabled()) {
                // Los permisos de notificación no están habilitados
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                startActivityForResult(intent, NOTIFICATION_PERMISSION_REQUEST)
            } else {
                // Los permisos de notificación ya están habilitados
                Toast.makeText(this, "Esta aplicación ya cuenta con los permisos necesarios.", Toast.LENGTH_SHORT).show()
            }
        }

        val version = findViewById<CardView>(R.id.sobreApp)
        version.setOnClickListener {
            val customDialog = version(this)
            customDialog.show()
        }

        val instrucciones = findViewById<CardView>(R.id.instrucciones)
        instrucciones.setOnClickListener {
            val url = "https://econnet-38506a24ec74.herokuapp.com/EX.html"
            val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intentWeb)
            finish()
        }



        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.newtoolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationIcon(R.drawable.back_arrow)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST) {
            val notificationManager = NotificationManagerCompat.from(this)
            if (notificationManager.areNotificationsEnabled()) {
                // Los permisos de notificación se habilitaron

            } else {
                // Los permisos de notificación no se habilitaron
                Toast.makeText(this, "No se han activado los permisos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
