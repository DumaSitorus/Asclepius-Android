package com.dicoding.asclepius.skinCencerPrediction.repository

import com.dicoding.asclepius.skinCencerPrediction.database.PredictionHistory
import com.dicoding.asclepius.skinCencerPrediction.database.PredictionHistoryDao
import kotlinx.coroutines.flow.Flow

class PredictionHistoryRepository(private val dao: PredictionHistoryDao) {
    fun getAllPredictions(): Flow<List<PredictionHistory>> = dao.getAllPredictions()
    suspend fun deletePrediction(predictionId: Long) = dao.deletePrediction(predictionId)
}