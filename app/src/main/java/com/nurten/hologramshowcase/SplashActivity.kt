package com.nurten.hologramshowcase

import android.content.Intent
import android.os.*
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        setContentView(R.layout.activity_splash)

        val eclipse = findViewById<View>(R.id.eclipseCircle)
        val pulse = AlphaAnimation(0.3f, 1f).apply {
            duration = 900
            repeatMode = AlphaAnimation.REVERSE
            repeatCount = AlphaAnimation.INFINITE
        }
        eclipse.startAnimation(pulse)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1400)
    }
}
