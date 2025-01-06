package com.hardik.notification.presentation.receiver

import com.hardik.notification.BASE_TAG

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hardik.notification.utillities.AlarmScheduler
import com.hardik.notification.utillities.AlertOffset
import com.hardik.notification.utillities.AlertOffsetConverter.toMilliseconds
import com.hardik.notification.utillities.Event
import com.hardik.notification.utillities.RepeatOption
import com.hardik.notification.utillities.RepeatOptionConverter
import java.util.Calendar

class NotificationReceiver : BroadcastReceiver() {
    private val TAG = BASE_TAG + NotificationReceiver::class.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            val event = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("event", Event::class.java)
            } else {
                intent.getParcelableExtra("event")
            }

            if (event != null) {

                val eventId = event.id //intent.getStringExtra("event_id") ?: "0"
                val title = event.title //intent.getStringExtra("event_title") ?: "Event Reminder"
                val description = event.description //intent.getStringExtra("event_description") ?: "You have an event!"
                Log.d(TAG, "onReceive: $eventId")

                showNotification(context, title, description, eventId)
                scheduleRepeatingNotification(context , event)
            }
        }
    }



    @SuppressLint("MissingPermission")
    private fun showNotification(context: Context, title: String, description: String, id: String) {
        val notificationManager = NotificationManagerCompat.from(context)

        // Create the notification channel for devices with API level 26 and above
        val channelId = "event_channel_id"
        val channelName = "Event Notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance).apply { setDescription("Notifications for scheduled events") }
        notificationManager.createNotificationChannel(channel)

        // Build notification
        val notification = NotificationCompat.Builder(context, "event_channel_id")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(id.hashCode(), notification)
    }

    private fun scheduleRepeatingNotification(context: Context, event: Event) {
        if (event.repeatOption != RepeatOption.NEVER && event.alertOffset != AlertOffset.NONE) { // Check if repeat option is not NEVER
            Log.i(TAG, "scheduleRepeatingNotification: Scheduling repeat for event: $event")

            val nextTriggerTime = calculateTriggerTime(alertOffset = event.alertOffset, repeatOption = event.repeatOption, baseTimeInMillis = event.timeInMillis)//System.currentTimeMillis() + event.repeatIntervalMillis
            val updatedEvent = event.copy(timeInMillis = nextTriggerTime)
            AlarmScheduler.updateAlarm(context, updatedEvent)
        }
    }

    // Calculate the actual trigger time based on AlertOffset and RepeatOption
    private fun calculateTriggerTime(alertOffset: AlertOffset, repeatOption: RepeatOption, baseTimeInMillis: Long): Long {
        val offsetInMillis = toMilliseconds(alertOffset) ?: return 0L // Return 0L if offsetInMillis is null
        val repeatOptionInMillis = RepeatOptionConverter.toMilliseconds(repeatOption) ?: return 0L // Return 0L if repeatOptionInMillis is null

        // Adjust base time based on repeat option
        val adjustedBaseTime = baseTimeInMillis + repeatOptionInMillis

        // Calculate the trigger time by subtracting the offset
        return adjustedBaseTime - offsetInMillis
    }
}