package com.sample.reminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "birthday_table")
data class Birthday(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val date: Long,  // Store the birthday as timestamp (milliseconds)
    val notifyBeforeDays: Int
)
