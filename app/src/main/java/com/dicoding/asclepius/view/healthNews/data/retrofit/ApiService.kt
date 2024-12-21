package com.dicoding.asclepius.view.healthNews.data.retrofit

import com.dicoding.asclepius.view.healthNews.data.response.HealthNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines")
    fun getTopHeadlines(
        @Query("q") query: String = "cancer",
        @Query("category") category: String = "health",
        @Query("language") language: String = "en",
        @Query("apiKey") apiKey: String
    ): Call<HealthNewsResponse>
}