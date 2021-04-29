package com.wuujcik.spacex.persistence

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Rocket(
    @SerializedName("id")
    var id: String = "0",
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("flickr_images")
    var flickr_images: List<String>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "0",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
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