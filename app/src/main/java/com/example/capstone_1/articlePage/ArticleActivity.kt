package com.example.capstone_1.articlePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstone_1.R

class ArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        supportActionBar?.hide()
    }
}