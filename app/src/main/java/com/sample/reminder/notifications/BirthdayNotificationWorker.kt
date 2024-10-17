package com.sample.reminder.notifications

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sample.reminder.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BirthdayNotificationWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val name = inputData.getString("name") ?: return Result.failure()
        val birthdayId = inputData.getLong("birthdayId", 0) // Get the birthday ID
        val time = inputData.getLong("time",0)
        sendNotification(name, birthdayId,time)

        return Result.success()
    }

    private fun sendNotification(name: String, birthdayId: Long,time:Long) {
        val readableTime = formatTime(time)
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        val notification = NotificationCompat.Builder(applicationContext, "birthday_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Birthday Reminder $readableTime")
            .setContentText("It's $name's birthday at $readableTime")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(birthdayId.toInt(), notification) // Use birthday ID as the notification ID
    }
    private fun formatTime(timeInMillis: Long): String {
        val dateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = Date(timeInMillis)
        return dateFormat.format(date)
    }
}
