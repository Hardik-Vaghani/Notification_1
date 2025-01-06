package com.hardik.notification.utillities

import android.content.Context
import com.hardik.notification.R

enum class AlertOffset {
    NONE,
    AT_TIME,
    BEFORE_5_MINUTES,
    BEFORE_10_MINUTES,
    BEFORE_15_MINUTES,
    BEFORE_30_MINUTES,
    BEFORE_1_HOUR,
    BEFORE_12_HOURS,
    BEFORE_1_DAY,
    BEFORE_3_DAYS,
    BEFORE_5_DAYS,
    BEFORE_1_WEEK,
    BEFORE_2_WEEKS,
    BEFORE_1_MONTH,
    BEFORE_CUSTOM_TIME,
}

object AlertOffsetConverter {

    // Convert enum to display string using string resources
    fun toDisplayString(context: Context, alertOffset: AlertOffset): String {
        return when (alertOffset) {
            AlertOffset.NONE -> context.getString(R.string.none)
            AlertOffset.AT_TIME -> context.getString(R.string.at_time)
            AlertOffset.BEFORE_5_MINUTES -> context.getString(R.string.before_5_minutes)
            AlertOffset.BEFORE_10_MINUTES -> context.getString(R.string.before_10_minutes)
            AlertOffset.BEFORE_15_MINUTES -> context.getString(R.string.before_15_minutes)
            AlertOffset.BEFORE_30_MINUTES -> context.getString(R.string.before_30_minutes)
            AlertOffset.BEFORE_1_HOUR -> context.getString(R.string.before_1_hour)
            AlertOffset.BEFORE_12_HOURS -> context.getString(R.string.before_12_hours)
            AlertOffset.BEFORE_1_DAY -> context.getString(R.string.before_1_day)
            AlertOffset.BEFORE_3_DAYS -> context.getString(R.string.before_3_days)
            AlertOffset.BEFORE_5_DAYS -> context.getString(R.string.before_5_days)
            AlertOffset.BEFORE_1_WEEK -> context.getString(R.string.before_1_week)
            AlertOffset.BEFORE_2_WEEKS -> context.getString(R.string.before_2_weeks)
            AlertOffset.BEFORE_1_MONTH -> context.getString(R.string.before_1_month)
            AlertOffset.BEFORE_CUSTOM_TIME -> context.getString(R.string.before_custom_time)
        }
    }

    // Convert a display string to enum
    fun fromDisplayString(context: Context, displayString: String): AlertOffset? {
        return when (displayString) {
            context.getString(R.string.none) -> AlertOffset.NONE
            context.getString(R.string.at_time) -> AlertOffset.AT_TIME
            context.getString(R.string.before_5_minutes) -> AlertOffset.BEFORE_5_MINUTES
            context.getString(R.string.before_10_minutes) -> AlertOffset.BEFORE_10_MINUTES
            context.getString(R.string.before_15_minutes) -> AlertOffset.BEFORE_15_MINUTES
            context.getString(R.string.before_30_minutes) -> AlertOffset.BEFORE_30_MINUTES
            context.getString(R.string.before_1_hour) -> AlertOffset.BEFORE_1_HOUR
            context.getString(R.string.before_12_hours) -> AlertOffset.BEFORE_12_HOURS
            context.getString(R.string.before_1_day) -> AlertOffset.BEFORE_1_DAY
            context.getString(R.string.before_3_days) -> AlertOffset.BEFORE_3_DAYS
            context.getString(R.string.before_5_days) -> AlertOffset.BEFORE_5_DAYS
            context.getString(R.string.before_1_week) -> AlertOffset.BEFORE_1_WEEK
            context.getString(R.string.before_2_weeks) -> AlertOffset.BEFORE_2_WEEKS
            context.getString(R.string.before_1_month) -> AlertOffset.BEFORE_1_MONTH
            context.getString(R.string.before_custom_time) -> AlertOffset.BEFORE_CUSTOM_TIME
            else -> null // Handle invalid strings gracefully
        }
    }

    // Convert AlertOffset enum to its value in milliseconds
    fun toMilliseconds(alertOffset: AlertOffset): Long? {
        return when (alertOffset) {
            AlertOffset.NONE -> null
            AlertOffset.AT_TIME -> 0L
            AlertOffset.BEFORE_5_MINUTES -> 5 * 60 * 1000L
            AlertOffset.BEFORE_10_MINUTES -> 10 * 60 * 1000L
            AlertOffset.BEFORE_15_MINUTES -> 15 * 60 * 1000L
            AlertOffset.BEFORE_30_MINUTES -> 30 * 60 * 1000L
            AlertOffset.BEFORE_1_HOUR -> 60 * 60 * 1000L
            AlertOffset.BEFORE_12_HOURS -> 12 * 60 * 60 * 1000L
            AlertOffset.BEFORE_1_DAY -> 24 * 60 * 60 * 1000L
            AlertOffset.BEFORE_3_DAYS -> 3 * 24 * 60 * 60 * 1000L
            AlertOffset.BEFORE_5_DAYS -> 5 * 24 * 60 * 60 * 1000L
            AlertOffset.BEFORE_1_WEEK -> 7 * 24 * 60 * 60 * 1000L
            AlertOffset.BEFORE_2_WEEKS -> 14 * 24 * 60 * 60 * 1000L
            AlertOffset.BEFORE_1_MONTH -> 30 * 24 * 60 * 60 * 1000L // Approximation
            AlertOffset.BEFORE_CUSTOM_TIME -> -1L // Here store custom time (long) get from user
        }
    }
}