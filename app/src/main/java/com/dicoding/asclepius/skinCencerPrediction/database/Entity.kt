package com.dicoding.asclepius.skinCencerPrediction.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "prediction_history")
data class PredictionHistory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val imageUri: String,
    val result: String,
    val confidence: Float,
    val timestamp: Long
) : Parcelable