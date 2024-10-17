package com.sample.reminder.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import com.sample.reminder.app.MyApplication
import com.sample.reminder.data.dao.BirthdayDao
import com.sample.reminder.data.model.Birthday

class BirthdayRepository(private val birthdayDao: BirthdayDao) {

    // LiveData that observes all birthday entries
    val allBirthdays: LiveData<List<Birthday>> = birthdayDao.getAllBirthdays()

    // Insert a birthday into the database
    suspend fun insert(birthday: Birthday) {
        try {
            birthdayDao.insert(birthday)
        }catch (e:Exception){
            Toast.makeText(MyApplication.getAppContext(),"Ex in insert"+ e.localizedMessage,Toast.LENGTH_LONG)
        }
    }

    // Delete a birthday from the database
    suspend fun delete(birthday: Birthday) {
        birthdayDao.delete(birthday)
    }
}
