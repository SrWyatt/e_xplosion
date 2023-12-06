package com.radio.repradio

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class pantallaInicio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFirstTime = isFirstTimeOpening()

        if (isFirstTime) {
            setContentView(R.layout.activity_pantalla_inicio)

            val siguienteButton = findViewById<Button>(R.id.siguiente)
            siguienteButton.setOnClickListener {
                val intent = Intent(this, itemviewer::class.java)
                startActivity(intent)
                finish()
            }

        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val title1 = findViewById<TextView>(R.id.title1)
        val text = title1.text.toString()

        val policiesPrivacyStartIndex = text.indexOf("POLÍTICAS DE PRIVACIDAD")
        val policiesPrivacyEndIndex = policiesPrivacyStartIndex + "POLÍTICAS DE PRIVACIDAD".length

        val termsConditionsStartIndex = text.indexOf("TÉRMINOS Y CONDICIONES DE USO")
        val termsConditionsEndIndex = termsConditionsStartIndex + "TÉRMINOS Y CONDICIONES DE USO".length

        val spannableString = SpannableString(text)

        val privacyClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                openUrl("https://www.app-privacy-policy.com/live.php?token=LepOhXg12wCxkKfq4GvzguQMsr13dxxf")
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#8EDAED")
                ds.isUnderlineText = false
            }
        }

        val termsClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                openUrl("https://www.app-privacy-policy.com/live.php?token=l83SgSsuUJKvhlMi3rJBUX8SsRFJfYX0")
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#8EDAED")
                ds.isUnderlineText = false
            }
        }

        spannableString.setSpan(privacyClickableSpan, policiesPrivacyStartIndex, policiesPrivacyEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(termsClickableSpan, termsConditionsStartIndex, termsConditionsEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        title1.text = spannableString
        title1.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun isFirstTimeOpening(): Boolean {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)

        if (isFirstTime) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("isFirstTime", false)
            editor.apply()
        }

        return isFirstTime
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
