package com.hardik.notification.utillities

import android.content.Context
import com.hardik.notification.R

enum class RepeatOption {
    NEVER,
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

object RepeatOptionConverter {
    // Convert enum to display string using string resources
    fun toDisplayString(context: Context, repeatOption: RepeatOption): String {
        return when (repeatOption) {
            RepeatOption.NEVER -> context.getString(R.string.never)
            RepeatOption.DAILY -> context.getString(R.string.every_day)
            RepeatOption.WEEKLY -> context.getString(R.string.every_week)
            RepeatOption.MONTHLY -> context.getString(R.string.every_month)
            RepeatOption.YEARLY -> context.getString(R.string.every_year)
        }
    }

    // Convert a display string to enum
    fun fromDisplayString(context: Context, displayString: String): RepeatOption? {
        return when (displayString) {
            context.getString(R.string.never) -> RepeatOption.NEVER
            context.getString(R.string.every_day) -> RepeatOption.DAILY
            context.getString(R.string.every_week) -> RepeatOption.WEEKLY
            context.getString(R.string.every_month) -> RepeatOption.MONTHLY
            context.getString(R.string.every_year) -> RepeatOption.YEARLY
            else -> null // Handle invalid strings gracefully
        }
    }

    fun toMilliseconds(repeatOption: RepeatOption): Long? {
        return when (repeatOption) {
            RepeatOption.NEVER -> null // No interval for "NEVER"
            RepeatOption.DAILY -> 24 * 60 * 60 * 1000L // 1 day in milliseconds
            RepeatOption.WEEKLY -> 7 * 24 * 60 * 60 * 1000L // 1 week in milliseconds
            RepeatOption.MONTHLY -> 30 * 24 * 60 * 60 * 1000L // 1 month (approximation) in milliseconds
            RepeatOption.YEARLY -> 365 * 24 * 60 * 60 * 1000L // 1 year (approximation) in milliseconds
        }
    }
}