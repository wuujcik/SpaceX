package com.wuujcik.spacex.persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.wuujcik.spacex.persistence.companyInfo.Headquarters

class Converters {
    @TypeConverter
    fun headquartersToJson(headquarters: Headquarters?): String {
        return Gson().toJson(headquarters)
    }

    @TypeConverter
    fun jsonToHeadquarters(value: String): Headquarters {
        return Gson().fromJson(value, Headquarters::class.java)
    }
}
