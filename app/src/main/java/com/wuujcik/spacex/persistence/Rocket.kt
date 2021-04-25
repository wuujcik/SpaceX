package com.wuujcik.spacex.persistence

import android.os.Parcel
import android.os.Parcelable

data class Rocket(
     var name: String? = null,
     var type: String? = null,
     var description: String? = null,
     var flickr_images: List<String>? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createStringArrayList()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(description)
        parcel.writeStringList(flickr_images)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rocket> {
        override fun createFromParcel(parcel: Parcel): Rocket {
            return Rocket(parcel)
        }

        override fun newArray(size: Int): Array<Rocket?> {
            return arrayOfNulls(size)
        }
    }
}
