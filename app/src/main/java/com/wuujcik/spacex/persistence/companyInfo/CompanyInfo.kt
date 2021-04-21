package com.wuujcik.spacex.persistence.companyInfo

import android.os.Parcel
import android.os.Parcelable

data class CompanyInfo(
    var id: String = "0",
    var name: String? = null,
    var founder: String? = null,
    var founded: Int? = null,
    var employees: Int? = null,
    var vehicles: Int? = null,
    var launch_sites: Int? = null,
    var test_sites: Int? = null,
    var ceo: String? = null,
    var cto: String? = null,
    var coo: String? = null,
    var cto_propulsion: String? = null,
    var valuation: Long? = null,
    var headquarters: Headquarters? = null,
    var summary: String? = null,
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "0",
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readParcelable(Headquarters::class.java.classLoader),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(founder)
        parcel.writeValue(founded)
        parcel.writeValue(employees)
        parcel.writeValue(vehicles)
        parcel.writeValue(launch_sites)
        parcel.writeValue(test_sites)
        parcel.writeString(ceo)
        parcel.writeString(cto)
        parcel.writeString(coo)
        parcel.writeString(cto_propulsion)
        parcel.writeValue(valuation)
        parcel.writeParcelable(headquarters, flags)
        parcel.writeString(summary)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CompanyInfo> {
        override fun createFromParcel(parcel: Parcel): CompanyInfo {
            return CompanyInfo(parcel)
        }

        override fun newArray(size: Int): Array<CompanyInfo?> {
            return arrayOfNulls(size)
        }
    }
}

data class Headquarters(
    var id: Int = 0,
    var address: String? = null,
    var city: String? = null,
    var state: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(address)
        parcel.writeString(city)
        parcel.writeString(state)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Headquarters> {
        override fun createFromParcel(parcel: Parcel): Headquarters {
            return Headquarters(parcel)
        }

        override fun newArray(size: Int): Array<Headquarters?> {
            return arrayOfNulls(size)
        }
    }
}