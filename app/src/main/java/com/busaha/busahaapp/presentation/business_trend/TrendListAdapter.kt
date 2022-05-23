package com.busaha.busahaapp.presentation.business_trend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.busaha.busahaapp.databinding.ItemTrendBinding
import com.busaha.busahaapp.domain.entity.Trend

class TrendListAdapter : ListAdapter<Trend, TrendListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemTrendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, trend: Trend) {
            val number = position + 1
            binding.trendTitle.text = "#".plus("$number ").plus(trend.title)
            binding.trendDesc.text = trend.desc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrendBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(position, data)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Trend>() {
            override fun areItemsTheSame(oldItem: Trend, newItem: Trend): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Trend, newItem: Trend): Boolean {
                return oldItem == newItem
            }
        }
    }
}