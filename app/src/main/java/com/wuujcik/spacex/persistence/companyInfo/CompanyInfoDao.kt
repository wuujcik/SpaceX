package com.wuujcik.spacex.persistence.companyInfo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CompanyInfoDao {

    @Query("SELECT * FROM company_info LIMIT 1")
    fun getCompanyInfo(): LiveData<CompanyInfo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CompanyInfo?)
}