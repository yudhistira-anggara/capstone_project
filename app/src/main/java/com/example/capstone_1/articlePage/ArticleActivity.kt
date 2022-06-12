package com.example.capstone_1.articlePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.capstone_1.R
import com.example.capstone_1.api.ArticleItem
import com.example.capstone_1.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        supportActionBar?.hide()

        setArticleView()
    }

    private fun setArticleView(){
        val article = intent.getParcelableExtra<ArticleItem>("article") as ArticleItem
        binding.txtJudulArtikel.text = article.title
        binding.txtArtikel.text = article.source

        Glide.with(this)
            .load(article.thumbnail)
            .into(binding.imgArtikel)
    }
}