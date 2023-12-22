package com.garbo.garboapplication.view.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.garbo.garboapplication.data.response.ResultListItem
import com.garbo.garboapplication.databinding.ItemRowHistoryBinding
import com.garbo.garboapplication.getDateFromTimestamp

class HistoryListAdapter(private val items: List<ResultListItem>) :
    RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultListItem) {
            with(binding) {
                Glide.with(itemView.context).load(item.photo_url).into(binding.ivItemPhoto)
                tvItemName.text = item.classification
                tvItemDate.text = item.timestamp?.let { getDateFromTimestamp(it) }
                tvItemPoints.text = when (item.classification) {
                    in setOf("cardboard", "paper") -> "+10 points"
                    "plastic" -> "+15 points"
                    in setOf("glass", "metal") -> "+20 points"
                    else -> "+0 points"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}