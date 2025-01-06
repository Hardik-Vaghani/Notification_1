package com.hardik.notification

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import com.hardik.notification.utillities.AlarmScheduler
import com.hardik.notification.utillities.AlarmScheduler.handlePermissionResult
import com.hardik.notification.utillities.AlertOffset
import com.hardik.notification.utillities.Event
import com.hardik.notification.utillities.RepeatOption

const val BASE_TAG = "B_"

class MainActivity : AppCompatActivity() {
    private val TAG = BASE_TAG + MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn)

        btn.apply { setOnClickListener { scheduleEvent() } }

    }

    private fun scheduleEvent() {

        val event = Event(
            id = "event1",
            title = "Meeting Reminder",
            description = "Don't forget your meeting at 3 PM!",
            timeInMillis = System.currentTimeMillis() + 5000, // Trigger in 1 minute
            repeatOption = RepeatOption.DAILY,
            alertOffset = AlertOffset.AT_TIME
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // This block only applies to Android 13 (API 33) and above
            val permissionStatus = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            )

            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                // Schedule the alarm
                AlarmScheduler.updateAlarm(this@MainActivity, event)
            } else {
                // Request the POST_NOTIFICATIONS permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    AlarmScheduler.PERMISSION_REQUEST_CODE
                )
            }
        } else {
            // For Android versions below API 33, no permission is needed
            AlarmScheduler.updateAlarm(this@MainActivity, event)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (handlePermissionResult(requestCode = requestCode, permissions = permissions, grantResults = grantResults)) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val event = Event(
                    id = "event1",
                    title = "Meeting Reminder",
                    description = "Don't forget your meeting at 3 PM!",
                    timeInMillis = System.currentTimeMillis() + 10000 // Trigger in 1 minute
                )

                // Permission granted, schedule the alarm
                AlarmScheduler.updateAlarm(this@MainActivity, event)
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(
                    this,
                    "Permission required to show notifications",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}

/*
enableEdgeToEdge()
setContentView(R.layout.activity_main)
ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
    insets
}*/
