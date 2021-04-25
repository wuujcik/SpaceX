package com.wuujcik.spacex.persistence

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Launch(
        var id: String = "0",
        var flight_number: Int? = null,
        var name: String? = null,
        var date_unix: Long? = null,
        @SerializedName("rocket")
        val rocketId: String? = null,
        val success: Boolean? = null,
        val upcoming: Boolean? = null,
        val details: String? = null,
        val links: Links? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString() ?: "0",
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readString(),
            parcel.readParcelable(Links::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeValue(flight_number)
        parcel.writeString(name)
        parcel.writeValue(date_unix)
        parcel.writeString(rocketId)
        parcel.writeValue(success)
        parcel.writeValue(upcoming)
        parcel.writeString(details)
        parcel.writeParcelable(links, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Launch> {
        override fun createFromParcel(parcel: Parcel): Launch {
            return Launch(parcel)
        }

        override fun newArray(size: Int): Array<Launch?> {
            return arrayOfNulls(size)
        }
    }
}

data class Links(
        var patch: Patch? = null,
        var article: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Patch::class.java.classLoader),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(patch, flags)
        parcel.writeString(article)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Links> {
        override fun createFromParcel(parcel: Parcel): Links {
            return Links(parcel)
        }

        override fun newArray(size: Int): Array<Links?> {
            return arrayOfNulls(size)
        }
    }
}

data class Patch(
        var small: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(small)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Patch> {
        override fun createFromParcel(parcel: Parcel): Patch {
            return Patch(parcel)
        }

        override fun newArray(size: Int): Array<Patch?> {
            return arrayOfNulls(size)
        }
    }
}
