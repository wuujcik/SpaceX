package com.wuujcik.spacex.persistence.companyInfo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wuujcik.spacex.persistence.Converters

@TypeConverters(Converters::class)
@Database(entities = [CompanyInfo::class], version = 1, exportSchema = false)
abstract class CompanyInfoDatabase  : RoomDatabase() {

    abstract fun companyInfoDao(): CompanyInfoDao

    companion object {

        @Volatile
        private var mInstance: CompanyInfoDatabase? = null

        fun getDatabase(context: Context): CompanyInfoDatabase {
            synchronized(this) {
                if (mInstance == null) {
                    // Create database
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CompanyInfoDatabase::class.java,
                        "company_info")
                        .fallbackToDestructiveMigration()
                        .build()

                    mInstance = instance
                }
                return mInstance!!
            }
        }
    }
}