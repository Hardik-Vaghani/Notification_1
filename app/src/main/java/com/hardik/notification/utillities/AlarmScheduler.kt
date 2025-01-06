package com.hardik.notification.utillities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hardik.notification.BASE_TAG
import com.hardik.notification.presentation.receiver.NotificationReceiver


object AlarmScheduler {
    private val TAG = BASE_TAG + AlarmScheduler::class.java.simpleName
    const val PERMISSION_REQUEST_CODE = 1001 // Unique code for notifications permission

    // Method to check and request POST_NOTIFICATIONS permission
    private fun ensureNotificationPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionStatus = ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            )

            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                // Request the permission from the user
                ActivityCompat.requestPermissions(
                    (context as Activity),
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    // Handle the result of the permission request
    fun handlePermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            return if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "POST_NOTIFICATIONS permission granted.")
                true
            } else {
                Log.e(TAG, "POST_NOTIFICATIONS permission denied.")
                false
            }
        }
        return false
    }

    // Rest of the AlarmScheduler code
    fun updateAlarm(context: Context, event: Event) {
        ensureNotificationPermission(context) // Ensure permission before setting an alarm
        cancelAlarm(context, event)
        scheduleExactTime(context, event.timeInMillis, event)
    }


    // Schedule the notification for a specific time.
    @SuppressLint("ScheduleExactAlarm")
    fun scheduleExactTime(context: Context, triggerTime: Long, event: Event) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        if (alarmManager == null) {
            Log.e(TAG, "AlarmManager is null, cannot schedule notification.")
            return
        }

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("event", event)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            event.id.hashCode(), // Unique request code for each event
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )

        Log.d(TAG, "Exact notification scheduled for event ID: ${event.id} at $triggerTime")
    }

    // Cancel the alarm for a specific event.
    fun cancelAlarm(context: Context, event: Event) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            event.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent)
            Log.d(TAG, "Alarm canceled for event ID: ${event.id}")
        } else {
            Log.e(TAG, "AlarmManager is null, cannot cancel alarm.")
        }
    }
}

