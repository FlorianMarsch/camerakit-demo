package de.florianmarsch.test.camerakit

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.camerakit.CameraKit
import com.camerakit.CameraKitView
import kotlinx.android.synthetic.main.activity_camera.*

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*


class CameraActivity : AppCompatActivity() , CameraKitView.ImageCallback {



    private lateinit var  cameraKitView: CameraKitView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        cameraKitView = findViewById<CameraKitView>(R.id.camera)
        button.setOnClickListener {
            button.setOnClickListener {  }
            cameraKitView.captureImage(this)
        }

        switch_face.setOnClickListener {
            cameraKitView.toggleFacing()
            when(cameraKitView.facing){
                CameraKit.FACING_FRONT -> switch_face.setImageResource(R.drawable.ic_camera_rear_24px)
                CameraKit.FACING_BACK ->switch_face.setImageResource(R.drawable.ic_camera_front_24px)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        cameraKitView.onStart()
    }

    override fun onResume() {
        super.onResume()
        cameraKitView.onResume()
    }

    override fun onPause() {
        cameraKitView.onPause()
        super.onPause()
    }

    override fun onStop() {
        cameraKitView.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onImage(p0: CameraKitView?, fileBytes: ByteArray?) {
        save(fileBytes)
    }


    private fun save(fileBytes: ByteArray?) {
        val file = File(applicationContext.cacheDir, UUID.randomUUID().toString() + ".jpg")
        val bos = BufferedOutputStream(FileOutputStream(file))
        bos.write(fileBytes)
        bos.flush()
        bos.close()

        val data = Intent()
        data.setData(Uri.parse(file.absolutePath))
        setResult(RESULT_OK, data)
        finish()
    }
}
