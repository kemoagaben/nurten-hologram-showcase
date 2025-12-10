package com.nurten.hologramshowcase

import android.hardware.*
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class HologramActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var videoView: VideoView
    private lateinit var flareOverlay: View
    private lateinit var rootView: View
    private lateinit var sensorManager: SensorManager
    private var accel: Sensor? = null
    private var mediaPlayer: MediaPlayer? = null
    private var lastX = 0f
    private var lastY = 0f
    private val smooth = 0.15f
    private val maxOffset = 40f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        setContentView(R.layout.activity_hologram)

        rootView = findViewById(R.id.holoRoot)
        videoView = findViewById(R.id.holoVideo)
        flareOverlay = findViewById(R.id.flareOverlay)

        setupVideo()
        setupSensors()
        flareIntro()

        rootView.setOnClickListener {
            videoView.seekTo(0)
            videoView.start()
        }
    }

    private fun setupVideo() {
        val uri = Uri.parse("android.resource://$packageName/${R.raw.sun_necklace_popout}")
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { mp ->
            mediaPlayer = mp
            mp.isLooping = true
            mp.start()
        }
        videoView.setOnCompletionListener {
            videoView.start()
        }
    }

    private fun setupSensors() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    private fun flareIntro() {
        flareOverlay.alpha = 1f
        flareOverlay.animate()
            .alpha(0f)
            .setDuration(1200)
            .setStartDelay(250)
            .withEndAction {
                flareOverlay.visibility = View.GONE
            }
            .start()
    }

    override fun onSensorChanged(e: SensorEvent?) {
        if (e?.sensor?.type != Sensor.TYPE_ACCELEROMETER) return

        lastX += (e.values[0] - lastX) * smooth
        lastY += (e.values[1] - lastY) * smooth

        val tx = (-lastX / 10f) * maxOffset
        val ty = (lastY / 10f) * maxOffset

        videoView.translationX = tx
        videoView.translationY = ty
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onResume() {
        super.onResume()
        accel?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
        videoView.start()
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        videoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
