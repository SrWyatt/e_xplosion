package com.radio.repradio

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.Toast
import android.widget.Toolbar

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text
import version
import java.io.IOException
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dialogNotification

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (!isGranted) {
            // Verificar si el Toast ya se mostró antes
            if (!sharedPreferences.getBoolean("permissionDeniedToastShown", false)) {
                // Mostrar el Toast
                val dialogoNotification = dialogNotification(this)
                dialogoNotification.show()
            }
        }
    }


    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playpauseImageView: ImageView
    private lateinit var volumeSeekbar: SeekBar
    private var isMediaPlayerPrepared = false
    private var isSearching = false
    private var isPlaybackStopped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)


        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_close,
            R.string.navigation_drawer_open
        )
        drawer.addDrawerListener(toggle)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        mediaPlayer = MediaPlayer()
        playpauseImageView = findViewById(R.id.playPauseImageView)
        volumeSeekbar = findViewById(R.id.volumeSeekBar)
        val progressBar = findViewById<ProgressBar>(R.id.circularProgressBar)

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        volumeSeekbar.max = maxVolume
        volumeSeekbar.progress = maxVolume

        val initialVolume = maxVolume / 2
        volumeSeekbar.progress = initialVolume
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, initialVolume, 0)

        mediaPlayer.setOnPreparedListener {
            isMediaPlayerPrepared = true
            mediaPlayer.start()
            playpauseImageView.setImageResource(R.drawable.pause_icon)
            playpauseImageView.isClickable = true
            progressBar.visibility = View.GONE
        }

        playpauseImageView.setOnClickListener {
            if (isMediaPlayerPrepared) {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    playpauseImageView.setImageResource(R.drawable.play_icon)
                } else {
                    mediaPlayer.start()
                    playpauseImageView.setImageResource(R.drawable.pause_icon)
                }
            } else {
                if (!isSearching) {
                    isSearching = true
                    try {
                        playpauseImageView.setImageResource(R.drawable.play_icon_gray)
                        progressBar.visibility = View.VISIBLE
                        mediaPlayer.setDataSource("https://stream.zeno.fm/daq52fzc9kttv")
                        mediaPlayer.prepareAsync()

                        mediaPlayer.setOnCompletionListener { mp ->
                            isMediaPlayerPrepared = false
                            isSearching = false
                            isPlaybackStopped = true // Marcar que la reproducción se detuvo
                            Toast.makeText(this, "Error al conectar, intente de nuevo.", Toast.LENGTH_SHORT).show()
                            playpauseImageView.isClickable = true
                            progressBar.visibility = View.GONE
                            playpauseImageView.setImageResource(R.drawable.play_icon)
                        }

                        val handler = Handler()
                        handler.postDelayed({
                            if (!isMediaPlayerPrepared) {
                                mediaPlayer.reset()
                                isSearching = false
                                playpauseImageView.isClickable = true
                                progressBar.visibility = View.GONE
                                playpauseImageView.setImageResource(R.drawable.play_icon)
                            }
                        }, 10000)

                        playpauseImageView.isClickable = false
                    } catch (e: IOException) {
                        isSearching = false
                        Toast.makeText(this, "Transmisión apagada, intenta más tarde.", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            }
        }

        // Agregar un Listener para la SeekBar para controlar el volumen
        volumeSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.inicio -> {
                Toast.makeText(this, "Ya estás en el inicio.", Toast.LENGTH_SHORT).show()
                drawer.closeDrawer(GravityCompat.START)
                true
            }

            R.id.e_connect -> {
                Toast.makeText(this, "Estamos trabajando en esta función.", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.instagram -> {
                val websiteUrl = "https://www.instagram.com/e_xplosion.live/"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
                startActivity(intent)
                drawer.closeDrawer(GravityCompat.START)
                true
            }

            R.id.facebook -> {
                val websiteUrl = "https://www.facebook.com/explosionradiolive/"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
                startActivity(intent)
                drawer.closeDrawer(GravityCompat.START)
                true
            }

            R.id.programacion -> {
                Toast.makeText(this, "Estamos trabajando en esta función.", Toast.LENGTH_SHORT).show()
                drawer.closeDrawer(GravityCompat.START)
                true
            }



            R.id.aboutus -> {
                startActivity(Intent(this, AcercaDe::class.java))
                true
                drawer.closeDrawer(GravityCompat.START)
                true
            }

            R.id.salir -> {
                finish()
                finishAffinity()
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                }
                mediaPlayer.release()
                true
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }
}
