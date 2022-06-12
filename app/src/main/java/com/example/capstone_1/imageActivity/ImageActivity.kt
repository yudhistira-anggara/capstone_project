package com.example.capstone_1.imageActivity

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.capstone_1.R
import com.example.capstone_1.camera.CameraActivity
import com.example.capstone_1.camera.rotateBitmap
import com.example.capstone_1.databinding.ActivityImageBinding
import com.example.capstone_1.diagnosePage.DiagnoseActivity
import com.example.capstone_1.mainPage.MainActivity
import java.io.File

class ImageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityImageBinding
    private var alertDialog : AlertDialog? = null

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
        val isGallery = intent.getBooleanExtra(isGallery,false)

        if(!isGallery){
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )
            binding.imgPenyakit.setImageBitmap(result)
        }else {
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.imgPenyakit.setImageBitmap(result)
        }


    }

    private fun setupAction() {
        binding.btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnUploadImage.setOnClickListener {
            processImage()
        }

    }

    private fun processImage() {
        val image = intent.getSerializableExtra(picture) as File

        if (alertDialog == null || !alertDialog?.isShowing!!){
            alertDialog = AlertDialog.Builder(this).apply {
                setMessage(context.getString(R.string.alert_message))
                setPositiveButton(context.getString(R.string.alert_button)) { p0, _ ->
                    val intent = Intent(this@ImageActivity,DiagnoseActivity::class.java)
                    intent.putExtra("picture",image)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    p0?.dismiss()
                    finish()
                }
            }.show()
        }else{
            alertDialog?.show()
        }

    }

    companion object {
        const val picture = "picture"
        const val isBackCamera = "isBackCamera"
        const val isGallery = "isGallery"
    }

}