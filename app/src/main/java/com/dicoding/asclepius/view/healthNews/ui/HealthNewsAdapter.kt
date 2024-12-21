package com.dicoding.asclepius.view.healthNews.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.databinding.HealthNewsItemBinding
import com.dicoding.asclepius.view.healthNews.data.response.ArticlesItem

class HealthNewsAdapter : RecyclerView.Adapter<HealthNewsAdapter.NewsViewHolder>() {
    private val newsList = mutableListOf<ArticlesItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setNewsList(news: List<ArticlesItem>) {
        newsList.clear()
        newsList.addAll(news)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = HealthNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    inner class NewsViewHolder(private val binding: HealthNewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem) {
            binding.titleNews.text = article.title
            Glide.with(binding.root.context)
                .load(article.urlToImage)
                .into(binding.imageNews)

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailHealthNewsActivity::class.java).apply {
                    putExtra(DetailHealthNewsActivity.EXTRA_TITLE, article.title)
                    putExtra(DetailHealthNewsActivity.EXTRA_IMAGE_URL, article.urlToImage)
                    putExtra(DetailHealthNewsActivity.EXTRA_DESCRIPTION, article.description)
                    putExtra(DetailHealthNewsActivity.EXTRA_PUBLISHED_AT, article.publishedAt)
                    putExtra(DetailHealthNewsActivity.EXTRA_CONTENT, article.content)
                    putExtra(DetailHealthNewsActivity.EXTRA_URL, article.url)
                }
                context.startActivity(intent)
            }
        }
    }
}
