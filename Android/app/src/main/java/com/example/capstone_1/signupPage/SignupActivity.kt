package com.example.capstone_1.signupPage

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstone_1.R
import com.example.capstone_1.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.txtSignIn.setPaintFlags(binding.txtSignIn.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

    }
}