package com.example.exerciseapp.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exerciseapp.R
import com.example.exerciseapp.databinding.ViewOverviewItemBinding
import com.example.exerciseapp.domain.model.Exercise

class OverviewListAdapter(
    private val onFavoriteClicked: (Exercise) -> Unit,
) : ListAdapter<Exercise, OverviewListAdapter.ViewHolder>(ExerciseDiff()) {

    fun updateItems(updatedList: List<Exercise>) {
        submitList(updatedList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewOverviewItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  = holder.bind(getItem(position))

    inner class ViewHolder(private val binding: ViewOverviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Exercise) {
            Glide.with(binding.root).load(item.imageUrl).into(binding.imageThumb)
            binding.textExerciseName.text = item.name
            if (item.isFavorite) {
                binding.imageFavorite.setImageResource(R.drawable.ic_baseline_star_24)
            } else {
                binding.imageFavorite.setImageResource(R.drawable.ic_baseline_star_outline_24)
            }
            binding.imageFavorite.setOnClickListener {
                onFavoriteClicked.invoke(item)
            }
        }
    }

    private class ExerciseDiff : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(
            oldItem: Exercise,
            newItem: Exercise
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Exercise,
            newItem: Exercise
        ): Boolean {
            return oldItem == newItem
        }
    }
}
