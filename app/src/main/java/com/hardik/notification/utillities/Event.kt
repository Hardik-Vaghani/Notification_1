package com.hardik.notification.utillities

import android.os.Parcel
import android.os.Parcelable

data class Event(
    val id: String,             // Unique identifier for the event
    val title: String,          // Title for the event notification
    val description: String,    // Description for the event notification
    val timeInMillis: Long,     // Time to trigger the notification
    val repeatOption: RepeatOption = RepeatOption.NEVER, // Whether the event should repeat (default false)
    val alertOffset: AlertOffset = AlertOffset.AT_TIME // Alert offset for the notification
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong(),
        RepeatOption.entries[parcel.readInt()],
        AlertOffset.entries[parcel.readInt()]
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeLong(timeInMillis)
        parcel.writeInt(repeatOption.ordinal)
        parcel.writeInt(alertOffset.ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}