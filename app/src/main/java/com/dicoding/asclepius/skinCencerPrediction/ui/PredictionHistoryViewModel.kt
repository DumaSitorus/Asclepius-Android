package com.dicoding.asclepius.skinCencerPrediction.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.skinCencerPrediction.database.PredictionHistory
import com.dicoding.asclepius.skinCencerPrediction.repository.PredictionHistoryRepository
import kotlinx.coroutines.launch

class PredictionHistoryViewModel(private val repository: PredictionHistoryRepository) : ViewModel() {
    val allPredictions: LiveData<List<PredictionHistory>> = repository.getAllPredictions().asLiveData()

    fun deletePrediction(prediction: PredictionHistory) {
        viewModelScope.launch {
            repository.deletePrediction(prediction.id)
        }
    }
}

class PredictionHistoryViewModelFactory(private val repository: PredictionHistoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PredictionHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PredictionHistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}