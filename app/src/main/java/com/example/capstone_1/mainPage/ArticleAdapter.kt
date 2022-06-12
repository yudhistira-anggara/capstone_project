package com.example.capstone_1.mainPage

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone_1.api.ArticleItem
import com.example.capstone_1.articlePage.ArticleActivity
import com.example.capstone_1.databinding.ItemArtikelBinding

class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var articleList :List<ArticleItem?>? = null

    fun setArticleList(articleList: List<ArticleItem?>?){
        this.articleList = articleList
    }

    inner class ArticleViewHolder (private val binding: ItemArtikelBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: ArticleItem){

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, ArticleActivity::class.java)
                intent.putExtra("article", data)

            }

            binding.apply {
                titleArtikel.text = data.title
                txtArtikel.text = data.source

                Glide.with(itemView)
                    .load(data.thumbnail)
                    .into(imgArtikel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = ItemArtikelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (articleList == null) 0
        else articleList?.size!!
    }
}