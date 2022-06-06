package com.example.capstone_1.diagnosePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstone_1.R

class DiagnoseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnose_page)
        supportActionBar?.hide()
    }
}