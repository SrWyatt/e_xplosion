package com.radio.repradio

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class itemviewer : AppCompatActivity() {

    private lateinit var btn_sitio: Button
    private lateinit var inicio: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itemviewer)


        inicio = findViewById(R.id.img_inicio)
        inicio.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_sitio = findViewById(R.id.btn_sitio)
        btn_sitio.setOnClickListener {
            val url = "https://econnet-38506a24ec74.herokuapp.com/EX.html"
            val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intentWeb)
            finish()

        }
    }

}
