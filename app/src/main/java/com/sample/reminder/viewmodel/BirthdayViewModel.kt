package com.sample.reminder.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sample.reminder.data.database.BirthdayDatabase
import com.sample.reminder.data.model.Birthday
import com.sample.reminder.notifications.NotificationScheduler
import com.sample.reminder.repository.BirthdayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BirthdayViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BirthdayRepository
    val allBirthdays: LiveData<List<Birthday>>

    // LiveData for user input
    val name = MutableLiveData<String>()
    val date = MutableLiveData<Long>()
    val notifyBeforeDays = MutableLiveData<Int>()

    // LiveData for observing the FAB click
    private val _fabClickEvent = MutableLiveData<Unit>()
    val fabClickEvent: LiveData<Unit> get() = _fabClickEvent


    init {
        val birthdayDao = BirthdayDatabase.getDatabase(application).birthdayDao()
        repository = BirthdayRepository(birthdayDao)
        allBirthdays = repository.allBirthdays
    }

    // Insert birthday and schedule notification
    suspend fun insert(birthday: Birthday) {
        repository.insert(birthday)
        NotificationScheduler(getApplication()).scheduleBirthdayNotification(birthday)
    }

    fun onSaveClicked(name: String, dateInMillis: Long, notifyBeforeDays: Int) {
        val birthday = Birthday(name = name, date = dateInMillis, notifyBeforeDays = notifyBeforeDays)

        // Insert birthday into database in a background thread
        viewModelScope.launch(Dispatchers.IO) {
            insert(birthday)
        }
    }


    // Called when the FAB is clicked
    fun onFabClick() {
        _fabClickEvent.value = Unit
    }
}
