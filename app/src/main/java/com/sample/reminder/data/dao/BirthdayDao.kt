package com.sample.reminder.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.reminder.data.model.Birthday

@Dao
interface BirthdayDao {

    @Query("SELECT * FROM birthday_table ORDER BY date ASC")
    fun getAllBirthdays(): LiveData<List<Birthday>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(birthday: Birthday)

    @Delete
    suspend fun delete(birthday: Birthday)
}
