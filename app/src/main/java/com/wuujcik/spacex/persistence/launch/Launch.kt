package com.wuujcik.spacex.persistence.launch

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Launch(
    var id: String = "0",
    var flight_number: Int? = null,
    var name: String? = null,
    var date_unix: Long? = null,
    var date_utc: String? = null,
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
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readParcelable(Links::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeValue(flight_number)
        parcel.writeString(name)
        parcel.writeValue(date_unix)
        parcel.writeString(date_utc)
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
    var id: Int = 0,
    var article: String? = null,
    var patch: Patch? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(Patch::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(article)
        parcel.writeParcelable(patch, flags)
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
    var id: Int = 0,
    var small: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
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


data class FilteredLaunches(
    var id: Int = 0,
    var docs: List<Launch>? = null,
    var totalDocs: Int? = null,
    var offset: Int? = null,
    var limit: Int? = null,
    var totalPages: Int? = null,
    var page: Int? = null,
    var pagingCounter: Int? = null,
    var hasPrevPage: Boolean? = null,
    var hasNextPage: Boolean? = null,
    var prevPage: Int? = null,
    var nextPage: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as Int,
        parcel.createTypedArrayList(Launch),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeTypedList(docs)
        parcel.writeValue(totalDocs)
        parcel.writeValue(offset)
        parcel.writeValue(limit)
        parcel.writeValue(totalPages)
        parcel.writeValue(page)
        parcel.writeValue(pagingCounter)
        parcel.writeValue(hasPrevPage)
        parcel.writeValue(hasNextPage)
        parcel.writeValue(prevPage)
        parcel.writeValue(nextPage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FilteredLaunches> {
        override fun createFromParcel(parcel: Parcel): FilteredLaunches {
            return FilteredLaunches(parcel)
        }

        override fun newArray(size: Int): Array<FilteredLaunches?> {
            return arrayOfNulls(size)
        }
    }
}
