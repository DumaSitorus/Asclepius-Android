package com.dicoding.asclepius.skinCencerPrediction.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.skinCencerPrediction.database.AppDatabase
import com.dicoding.asclepius.skinCencerPrediction.repository.PredictionHistoryRepository
import com.dicoding.asclepius.databinding.ActivityCheckupHistoryBinding
import com.dicoding.asclepius.skinCencerPrediction.database.PredictionHistory
import kotlinx.coroutines.launch

class CheckupHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckupHistoryBinding
    private lateinit var viewModel: PredictionHistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckupHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CheckupHistoryAdapter(
            onItemDetail = { prediction ->
                openPredictionDetail(prediction)
            },
            onItemDelete = { prediction ->
                showDeleteConfirmationDialog(prediction)
            }
        )

        binding.rvPredictionHistory.layoutManager = LinearLayoutManager(this)
        binding.rvPredictionHistory.adapter = adapter

        val database = AppDatabase.getDatabase(this)
        val repository = PredictionHistoryRepository(database.checkupHistoryDao())
        val factory = PredictionHistoryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[PredictionHistoryViewModel::class.java]

        viewModel.allPredictions.observe(this) { predictions ->
            adapter.submitList(predictions)
        }
    }

    private fun showDeleteConfirmationDialog(prediction: PredictionHistory) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are you sure you want to delete this data?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                deleteDataFromDatabase(prediction)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setTitle("Confirm Delete")
        alert.show()
    }

    private fun deleteDataFromDatabase(prediction: PredictionHistory) {
        lifecycleScope.launch {
            viewModel.deletePrediction(prediction)
        }
    }

    private fun openPredictionDetail(prediction: PredictionHistory) {
        val intent = Intent(this, CheckupHistoryDetailActivity::class.java).apply {
            putExtra("PREDICTION_DETAIL", prediction)
        }
        startActivity(intent)
    }
}
