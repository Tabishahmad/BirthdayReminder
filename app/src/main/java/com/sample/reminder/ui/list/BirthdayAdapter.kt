package com.sample.reminder.ui.list

import com.sample.reminder.data.model.Birthday
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.reminder.databinding.ItemBirthdayBinding

class BirthdayAdapter : ListAdapter<Birthday, BirthdayAdapter.BirthdayViewHolder>(BirthdayDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthdayViewHolder {
        val binding = ItemBirthdayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BirthdayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BirthdayViewHolder, position: Int) {
        val birthday = getItem(position)
        holder.bind(birthday)
    }

    class BirthdayViewHolder(private val binding: ItemBirthdayBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(birthday: Birthday) {
            binding.birthday = birthday
            binding.executePendingBindings() // Ensure data binding updates the view immediately
        }
    }
}

class BirthdayDiffCallback : DiffUtil.ItemCallback<Birthday>() {
    override fun areItemsTheSame(oldItem: Birthday, newItem: Birthday): Boolean {
        return oldItem.id == newItem.id // Adjust based on your model's unique identifier
    }

    override fun areContentsTheSame(oldItem: Birthday, newItem: Birthday): Boolean {
        return oldItem == newItem
    }
}
