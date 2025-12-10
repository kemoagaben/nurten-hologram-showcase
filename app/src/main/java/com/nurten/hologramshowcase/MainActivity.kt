package com.nurten.hologramshowcase

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.systemUiVisibility =
            android.view.View.SYSTEM_UI_FLAG_FULLSCREEN or
            android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        setContentView(R.layout.activity_main)

        fun openHolo(id: Int) {
            findViewById<Button>(id).setOnClickListener {
                startActivity(Intent(this, HologramActivity::class.java))
            }
        }

        openHolo(R.id.btnSun)
        openHolo(R.id.btnNature)
        openHolo(R.id.btnLove)
        openHolo(R.id.btnTimeless)
        openHolo(R.id.btnWater)

        findViewById<Button>(R.id.btnArMode).setOnClickListener {
            startActivity(Intent(this, ArHologramActivity::class.java))
        }
    }
}
