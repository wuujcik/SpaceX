package com.wuujcik.spacex.persistence.companyInfo

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_info")
data class CompanyInfo(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String = "0",
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "founder")
    var founder: String? = null,
    @ColumnInfo(name = "founded")
    var founded: Int? = null,
    @ColumnInfo(name = "employees")
    var employees: Int? = null,
    @ColumnInfo(name = "vehicles")
    var vehicles: Int? = null,
    @ColumnInfo(name = "launch_sites")
    var launch_sites: Int? = null,
    @ColumnInfo(name = "test_sites")
    var test_sites: Int? = null,
    @ColumnInfo(name = "ceo")
    var ceo: String? = null,
    @ColumnInfo(name = "cto")
    var cto: String? = null,
    @ColumnInfo(name = "coo")
    var coo: String? = null,
    @ColumnInfo(name = "cto_propulsion")
    var cto_propulsion: String? = null,
    @ColumnInfo(name = "valuation")
    var valuation: Long? = null,
    @ColumnInfo(name = "headquarters")
    var headquarters: Headquarters? = null,
    @ColumnInfo(name = "summary")
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

@Entity(tableName = "headquarters")
data class Headquarters(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "address")
    var address: String? = null,
    @ColumnInfo(name = "city")
    var city: String? = null,
    @ColumnInfo(name = "state")
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