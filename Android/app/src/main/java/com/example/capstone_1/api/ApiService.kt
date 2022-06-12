package com.example.capstone_1.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET(".json")
    fun getArticle() : Call<ArticleResponse>
}