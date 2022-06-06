package com.example.capstone_1.imageActivity

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.capstone_1.R
import com.example.capstone_1.camera.CameraActivity
import com.example.capstone_1.camera.rotateBitmap
import com.example.capstone_1.databinding.ActivityImageBinding
import com.example.capstone_1.mainPage.MainActivity
import java.io.File

class ImageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        getResult()
        setupAction()
    }

    private fun getResult() {
        val myFile = intent.getSerializableExtra(picture) as File
        val isBackCamera = intent.getBooleanExtra(isBackCamera, true) as Boolean

        val result = rotateBitmap(
            BitmapFactory.decodeFile(myFile.path),
            isBackCamera
        )

        binding.imgPenyakit.setImageBitmap(result)
    }

    private fun setupAction() {
        binding.btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }


    companion object {
        const val picture = "picture"
        const val isBackCamera = "isBackCamera"
    }

}