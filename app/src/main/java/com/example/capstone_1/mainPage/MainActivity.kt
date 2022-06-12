package com.example.capstone_1.mainPage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.capstone_1.R
import com.example.capstone_1.camera.CameraActivity
import com.example.capstone_1.camera.uriToFile
import com.example.capstone_1.databinding.ActivityMainBinding
import com.example.capstone_1.diagnosePage.DiagnoseActivity
import com.example.capstone_1.imageActivity.ImageActivity
import com.example.capstone_1.loginPage.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private var alertDialog : AlertDialog? = null
    private lateinit var mainViewModel : MainViewModel
    private lateinit var adapter: ArticleAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        if (firebaseUser == null) {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
            return
        }

        binding.txtHaloUser.text = "Halo , ${firebaseUser.displayName.toString()}"

        setupViewModel()

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        supportActionBar?.hide()

    }

    private fun setupAction() {

        binding.apply {

            btnCamera.setOnClickListener {
                startCameraX()
            }

            btnUpload.setOnClickListener {
                startGallery()
            }

            imgLogout.setOnClickListener {
                logOut()
            }
        }
    }

    private fun startCameraX(){
        val intent = Intent(this,CameraActivity::class.java)
        startActivity(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun logOut(){

        if (alertDialog == null || !alertDialog?.isShowing!!){
            alertDialog = AlertDialog.Builder(this).apply {
                setTitle(context.getString(R.string.alert_logoutVerification_title))
                setMessage(context.getString(R.string.alert_logoutVerification_message))
                setPositiveButton(context.getString(R.string.alert_logoutVerification_button)) { p0, _ ->
                    val intent = Intent(this@MainActivity, DiagnoseActivity::class.java)
                    auth.signOut()
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

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == RESULT_OK){
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            val intent = Intent(this@MainActivity,ImageActivity::class.java)

            intent.putExtra("picture",myFile)
            intent.putExtra("isGallery",true)
            startActivity(intent)
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext,it) == PackageManager.PERMISSION_GRANTED
    }

    private fun setupViewModel(){
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.getArticle().observe(this){
            if (it != null){
                adapter.setArticleList(it)
                adapter.notifyDataSetChanged()
            }
        }
    }


}