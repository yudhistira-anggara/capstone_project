package com.example.capstone_1.mainPage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_1.api.ApiConfig
import com.example.capstone_1.api.ArticleItem
import com.example.capstone_1.api.ArticleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    val listArticle = MutableLiveData<List<ArticleItem?>?>()

    fun setArticle() {
        ApiConfig.getApiService().getArticle()
            .enqueue(object : Callback<ArticleResponse> {
                override fun onResponse(
                    call: Call<ArticleResponse>,
                    response: Response<ArticleResponse>
                ) {
                    if (response.isSuccessful) {
                        listArticle.postValue(response.body()?.article)
                        Log.d("awikwok","${response.body()}")
                    } else {
                        listArticle.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    listArticle.postValue(null)
                }

            })
    }

    fun getArticle(): MutableLiveData<List<ArticleItem?>?> {
        return listArticle
    }
}