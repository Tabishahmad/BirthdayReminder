package com.sample.reminder.notifications

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.sample.reminder.app.MyApplication
import com.sample.reminder.data.model.Birthday
import java.util.concurrent.TimeUnit

class NotificationScheduler(private val context: Context) {

    fun scheduleBirthdayNotification(birthday: Birthday) {
        println("insert birthday " + birthday)
        val notifyTime = calculateNotificationTime(birthday)
        println("insert notifyTime " + notifyTime)
        // Create the input data to send to the Worker
        val inputData = Data.Builder()
            .putString("name", birthday.name)
            .putLong("time", birthday.date)
            .putLong("birthdayId", birthday.id.toLong()) // Pass the birthday ID
            .build()

        // Schedule the WorkManager to run at a specific time using `OneTimeWorkRequest`
        val workRequest = OneTimeWorkRequestBuilder<BirthdayNotificationWorker>()
            .setInitialDelay(notifyTime, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
        showToast(notifyTime)
    }
    private fun showToast(notifyTime: Long) {
        try {
            val notifyTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(notifyTime)
            val notifyTimeInMinutes = notifyTimeInSeconds / 60

            // Run on the main thread to show the Toast
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    MyApplication.getAppContext(),
                    "Notification scheduled for $notifyTimeInMinutes minutes ($notifyTimeInSeconds seconds) from now",
                    Toast.LENGTH_LONG
                ).show()
            }
        }catch (e:Exception){}
    }

    private fun calculateNotificationTime(birthday: Birthday): Long {
        // Calculate the delay for the notification (3 or 5 days before)
        val birthdayMillis = birthday.date  // Birthday timestamp in milliseconds
        val notifyBeforeDays = birthday.notifyBeforeDays  // Either 3 or 5 days

        // Get the current time
        val currentTime = System.currentTimeMillis()

        // Calculate when the notification should fire
        return birthdayMillis - TimeUnit.DAYS.toMillis(notifyBeforeDays.toLong()) - currentTime
    }
}
