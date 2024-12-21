package com.dicoding.asclepius.skinCencerPrediction.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.databinding.ActivityCheckupHistoryDetailBinding
import com.dicoding.asclepius.skinCencerPrediction.database.PredictionHistory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckupHistoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckupHistoryDetailBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckupHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        @Suppress("DEPRECATION")
        val prediction = intent.getParcelableExtra<PredictionHistory>("PREDICTION_DETAIL")
        prediction?.let {
            binding.apply {
                resultImage.setImageURI(Uri.parse(it.imageUri))
                resultText.text = it.result
                confidenceText.text = "${it.confidence.toInt()}%"
                checkupTime.text = SimpleDateFormat("dd/MM/yyyy (HH:mm)", Locale.getDefault()).format(Date(it.timestamp))
            }
        }
    }
}