package com.dicoding.asclepius.view.healthNews.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.R
import com.dicoding.asclepius.view.healthNews.data.response.ArticlesItem
import com.dicoding.asclepius.view.healthNews.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class HealthNewsViewModel(application: Application) : AndroidViewModel(application) {
    private val _healthNews = MutableLiveData<List<ArticlesItem>>()
    val healthNews: LiveData<List<ArticlesItem>> = _healthNews

    fun getHealthNews() {
        viewModelScope.launch {
            try {
                val apiKey = getApplication<Application>().getString(R.string.news_api_key)
                val response = ApiConfig.getApiService().getTopHeadlines(apiKey = apiKey).awaitResponse()
                if (response.isSuccessful) {
                    _healthNews.postValue(response.body()?.articles?.filterNotNull() ?: listOf())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
