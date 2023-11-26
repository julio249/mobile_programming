package com.example.final_project.models

import android.os.Parcel
import android.os.Parcelable

data class Festival(
    val eventName: String?,
    val date: String?,
    val venue: String?,
    val imageUrl: String?,
    val ticketUrl: String?,
    val lineup: MutableList<Artist>
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        ArrayList<Artist>().apply {
            parcel.readList(this, Artist::class.java.classLoader)
        }
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(eventName)
        parcel.writeString(date)
        parcel.writeString(venue)
        parcel.writeString(imageUrl)
        parcel.writeString(ticketUrl)
        parcel.writeList(lineup)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Festival> {
        override fun createFromParcel(parcel: Parcel): Festival {
            return Festival(parcel)
        }

        override fun newArray(size: Int): Array<Festival?> {
            return arrayOfNulls(size)
        }
    }
}
