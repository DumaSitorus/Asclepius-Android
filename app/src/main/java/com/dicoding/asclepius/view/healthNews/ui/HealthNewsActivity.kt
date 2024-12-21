package com.dicoding.asclepius.view.healthNews.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.ActivityHealthNewsBinding

class HealthNewsActivity : AppCompatActivity() {
    private val viewModel: HealthNewsViewModel by viewModels()
    private lateinit var newsAdapter: HealthNewsAdapter
    private lateinit var binding: ActivityHealthNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsAdapter = HealthNewsAdapter()
        binding.rvHealthNews.layoutManager = LinearLayoutManager(this)
        binding.rvHealthNews.adapter = newsAdapter

        viewModel.healthNews.observe(this) { articles ->
            newsAdapter.setNewsList(articles)
        }
        viewModel.getHealthNews()
    }
}