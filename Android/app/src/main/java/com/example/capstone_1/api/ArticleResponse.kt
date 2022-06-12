package com.example.capstone_1.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field
import java.security.CodeSource

data class ArticleResponse(

	@field:SerializedName("article")
	val article: List<ArticleItem?>? = null
)

data class ArticleItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)