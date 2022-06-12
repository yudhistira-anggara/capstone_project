package com.example.capstone_1.diagnosePage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.capstone_1.databinding.ActivityDiagnosePageBinding
import com.example.capstone_1.mainPage.MainActivity
import com.example.capstone_1.ml.SkinPrediction
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File

class DiagnoseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiagnosePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnosePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        outputGenerator()
        setupAction()
    }

    private fun setupAction() {

        binding.apply {
            btnKembali.setOnClickListener {
                startActivity(Intent(this@DiagnoseActivity,MainActivity::class.java))
                finish()
            }

            btnRekomendasiPerawatan.setOnClickListener {
                startActivity(Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/search?q=${txtDiagnosaPenyakit.text}")
                ))
            }
        }
    }

    private fun outputGenerator() {
        val getFile = intent.getSerializableExtra(picture) as File
        val result = BitmapFactory.decodeFile(getFile.path)

        binding.image.setImageBitmap(result)

        //load labels
        val fileName = "skin_labels.txt"
        val inputLabel = application.assets.open(fileName).bufferedReader().use { it.readText() }.split("\n")

        var resize = Bitmap.createScaledBitmap(result,448,448,true)
        val model = SkinPrediction.newInstance(this)
        var byteBuffer = TensorImage.fromBitmap(resize).buffer

// Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        var max = getMax(outputFeature0.floatArray)

        binding.txtDiagnosaPenyakit.text = inputLabel[max]

// Releases model resources if no longer used.
        model.close()
    }


    private fun getMax(arr : FloatArray) : Int {
        var index = 0
        var bestConfid = 0.0f

        for (i in arr.indices) {
            if (arr[i] > bestConfid) {
                index = i
                bestConfid = arr[i]
            }
            Log.d("$i","$index , $bestConfid , ${arr[i]}")
        }
        return index
    }

    companion object {
        const val picture = "picture"
    }
}