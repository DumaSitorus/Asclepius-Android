package com.dicoding.asclepius.view.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityHomeBinding
import com.dicoding.asclepius.skinCencerPrediction.ui.CheckupHistoryActivity
import com.dicoding.asclepius.skinCencerPrediction.ui.MainActivity
import com.dicoding.asclepius.view.healthNews.ui.HealthNewsActivity
import com.dicoding.asclepius.view.healthNews.ui.HealthNewsAdapter
import com.dicoding.asclepius.view.healthNews.ui.HealthNewsViewModel

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var newsAdapter: HealthNewsAdapter
    private val viewModel: HealthNewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheckup.setOnClickListener(this)
        binding.btnHistoryCheckup.setOnClickListener(this)
        binding.titleNewsSection.setOnClickListener(this)

        newsAdapter = HealthNewsAdapter()
        binding.rvHealthNews.layoutManager = LinearLayoutManager(this)
        binding.rvHealthNews.adapter = newsAdapter

        viewModel.healthNews.observe(this) { articles ->
            val limitedArticles = articles.take(5)
            newsAdapter.setNewsList(limitedArticles)
        }
        viewModel.getHealthNews()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCheckup->{
                val intentMain = Intent(this@HomeActivity, MainActivity::class.java)
                startActivity(intentMain)
            }
            R.id.btnHistoryCheckup->{
                val intentHistoryResult = Intent(this@HomeActivity, CheckupHistoryActivity::class.java)
                startActivity(intentHistoryResult)
            }
            R.id.title_news_section->{
                val intentNews = Intent(this@HomeActivity, HealthNewsActivity::class.java)
                startActivity(intentNews)
            }
        }
    }
}