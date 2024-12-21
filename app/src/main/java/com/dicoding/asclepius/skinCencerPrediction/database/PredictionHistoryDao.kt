package com.dicoding.asclepius.skinCencerPrediction.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PredictionHistoryDao {
    @Insert
    suspend fun insert(predictionHistory: PredictionHistory)

    @Query("SELECT * FROM prediction_history ORDER BY timestamp DESC")
    fun getAllPredictions(): Flow<List<PredictionHistory>>

    @Query("DELETE FROM prediction_history WHERE id = :predictionId")
    suspend fun deletePrediction(predictionId: Long)
}