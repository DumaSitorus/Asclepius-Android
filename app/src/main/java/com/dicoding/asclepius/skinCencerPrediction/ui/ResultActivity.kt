package com.dicoding.asclepius.skinCencerPrediction.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.skinCencerPrediction.database.AppDatabase
import com.dicoding.asclepius.skinCencerPrediction.database.PredictionHistory
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityResultBinding
    private lateinit var database: AppDatabase

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val isCancer = intent.getStringExtra("IS_CANCER") ?: "Unknown"
        val confidence = intent.getFloatExtra("CONFIDENCE", 0.0f)
        val imageUriString = intent.getStringExtra("IMAGE_URI")
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            binding.resultImage.setImageURI(imageUri)
        }

        binding.resultText.text = "${isCancer} ${confidence.toInt()}%"

        database = AppDatabase.getDatabase(applicationContext)
        binding.btnSaveHistory.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_save_history->{
                val isCancer = intent.getStringExtra("IS_CANCER") ?: "Unknown"
                val confidence = intent.getFloatExtra("CONFIDENCE", 0.0f)
                val imageUriString = intent.getStringExtra("IMAGE_URI") ?: ""

                val timestamp = System.currentTimeMillis()

                val predictionHistory = PredictionHistory(
                    imageUri = imageUriString,
                    result = isCancer,
                    confidence = confidence,
                    timestamp = timestamp
                )

                lifecycleScope.launch {
                    database.checkupHistoryDao().insert(predictionHistory)
                    Toast.makeText(this@ResultActivity, "Prediction data successfully saved!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}