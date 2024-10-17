package com.sample.reminder.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.reminder.data.model.Birthday
import com.sample.reminder.ui.list.BirthdayAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("items")
    fun setItems(recyclerView: RecyclerView, birthdays: List<Birthday>?) {
        val adapter = recyclerView.adapter as? BirthdayAdapter
        birthdays?.let {
            adapter?.submitList(it)
        }
    }
}
