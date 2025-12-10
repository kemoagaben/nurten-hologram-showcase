package com.nurten.hologramshowcase

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.gorisse.thomas.sceneform.ArFragment
import com.gorisse.thomas.sceneform.math.Position
import com.gorisse.thomas.sceneform.node.ModelNode
import com.gorisse.thomas.sceneform.scene.await
import com.gorisse.thomas.sceneform.scene.setOnTapArPlaneListener
import com.google.ar.core.HitResult
import kotlinx.coroutines.*

class ArHologramActivity : AppCompatActivity() {
    private lateinit var arFragment: ArFragment
    private val uiScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        setContentView(R.layout.activity_ar_hologram)

        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, _, _ ->
            placeNecklace(hitResult)
        }
    }

    private fun placeNecklace(hitResult: HitResult) {
        arFragment.setOnTapArPlaneListener(null)
        uiScope.launch {
            val modelInstance = arFragment.modelLoader.createModelInstance(
                assetFileLocation = "gunesin_sicakligi.glb"
            ).await()

            val node = ModelNode(
                modelInstance = modelInstance,
                anchor = hitResult.createAnchor()
            ).apply {
                position = Position(0f, 0.05f, 0f)
                scale = Position(0.2f, 0.2f, 0.2f)
                isSmoothTransformEnabled = true
                isShadowReceiver = true
                isShadowCaster = true
            }

            arFragment.addChild(node)
            node.isSelectable = true
        }
    }
}
