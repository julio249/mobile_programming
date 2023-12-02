package com.example.final_project.models

import android.os.Parcel
import android.os.Parcelable

data class Gallery(
    val name: String?,
    val image_url: String?,
    val url: String?,
    val address1: String?,
    val city: String?,
    val state: String?,
    val zipCode: String?

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(image_url)
        parcel.writeString(url)
        parcel.writeString(address1)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(zipCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Gallery> {
        override fun createFromParcel(parcel: Parcel): Gallery {
            return Gallery(parcel)
        }

        override fun newArray(size: Int): Array<Gallery?> {
            return arrayOfNulls(size)
        }
    }
}