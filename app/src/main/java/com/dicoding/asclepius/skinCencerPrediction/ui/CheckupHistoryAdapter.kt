package com.dicoding.asclepius.skinCencerPrediction.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.skinCencerPrediction.database.PredictionHistory
import com.dicoding.asclepius.databinding.CheckupHistoryItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckupHistoryAdapter(
    private val onItemDetail: (PredictionHistory) -> Unit,
    private val onItemDelete: (PredictionHistory) -> Unit
) : ListAdapter<PredictionHistory, CheckupHistoryAdapter.PredictionViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionViewHolder {
        val binding = CheckupHistoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PredictionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PredictionViewHolder, position: Int) {
        val prediction = getItem(position)
        holder.bind(prediction)
    }

    inner class PredictionViewHolder(private val binding: CheckupHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(prediction: PredictionHistory) {
            val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(Date(prediction.timestamp))

            binding.apply {
                tvItemName.text = "$formattedDate"
                tvItemDescription.text = "${prediction.result} ${prediction.confidence.toInt()}%"
                imgItemPhoto.setImageURI(Uri.parse(prediction.imageUri))

                btnDetail.setOnClickListener {
                    onItemDetail(prediction)
                }
                btnDelete.setOnClickListener{
                    onItemDelete(prediction)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PredictionHistory>() {
            override fun areItemsTheSame(oldItem: PredictionHistory, newItem: PredictionHistory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PredictionHistory, newItem: PredictionHistory): Boolean {
                return oldItem == newItem
            }
        }
    }
}