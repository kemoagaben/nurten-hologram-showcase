package com.nurten.hologramshowcase

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class ArHologramActivity : AppCompatActivity() {
    private lateinit var arFragment: ArFragment
    private var placed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        setContentView(R.layout.activity_ar_hologram)

        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            if (placed) return@setOnTapArPlaneListener
            placed = true
            ModelRenderable.builder()
                .setSource(this, Uri.parse("gunesin_sicakligi.glb"))
                .setIsFilamentGltf(true)
                .build()
                .thenAccept { renderable ->
                    val anchorNode = AnchorNode(hitResult.createAnchor())
                    anchorNode.setParent(arFragment.arSceneView.scene)
                    TransformableNode(arFragment.transformationSystem).apply {
                        this.renderable = renderable
                        localScale = Vector3(0.2f, 0.2f, 0.2f)
                        setParent(anchorNode)
                        select()
                    }
                }
        }
    }
}
