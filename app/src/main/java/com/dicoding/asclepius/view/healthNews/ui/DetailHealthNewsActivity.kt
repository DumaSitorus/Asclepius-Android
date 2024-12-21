package com.dicoding.asclepius.view.healthNews.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.asclepius.databinding.ActivityDetailHealthNewsBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailHealthNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHealthNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHealthNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(EXTRA_TITLE)
        val imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val publishedAt = intent.getStringExtra(EXTRA_PUBLISHED_AT)
        val content = intent.getStringExtra(EXTRA_CONTENT)
        val url = intent.getStringExtra(EXTRA_URL)

        binding.titleTextView.text = title
        binding.publishedAtTextView.text = formatPublishedDate(publishedAt)
        binding.descriptionTextView.text = description
        binding.contentTextView.text = content

        Glide.with(this)
            .load(imageUrl)
            .into(binding.imageView)

        binding.readMoreButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun formatPublishedDate(dateString: String?): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val date: Date? = dateString?.let { inputFormat.parse(it) }
            date?.let { outputFormat.format(it) } ?: ""
        } catch (e: ParseException) {
            e.printStackTrace()
            dateString ?: ""
        }
    }

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_IMAGE_URL = "extra_image_url"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_PUBLISHED_AT = "extra_published_at"
        const val EXTRA_CONTENT = "extra_content"
        const val EXTRA_URL = "extra_url"
    }
}