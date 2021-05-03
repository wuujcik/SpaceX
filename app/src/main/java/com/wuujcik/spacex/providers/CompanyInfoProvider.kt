package com.wuujcik.spacex.providers

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.wuujcik.spacex.persistence.companyInfo.CompanyInfo
import com.wuujcik.spacex.persistence.companyInfo.CompanyInfoDao
import com.wuujcik.spacex.persistence.companyInfo.CompanyInfoDatabase
import com.wuujcik.spacex.webservices.SpaceXApi.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CompanyInfoProvider (context: Context) {

    private val companyInfoDao: CompanyInfoDao by lazy {
        CompanyInfoDatabase.getDatabase(context).companyInfoDao()
    }

    val companyInfo: LiveData<CompanyInfo?> = companyInfoDao.getCompanyInfo()

    fun requestCompanyInfo(
        scope: CoroutineScope
    ) {
        scope.launch {
            try {
                val companyInfo: CompanyInfo? =
                    retrofitService.getCompanyInfo()
                        .awaitResponse()
                        .body()
                insertCompanyInfo(companyInfo)
            } catch (e: Exception) {
                Log.e(TAG, "getCompanyInfo failed with exception: $e")
            }
        }
    }

    suspend fun insertCompanyInfo(companyInfo: CompanyInfo?) {
        try {
            companyInfoDao.insert(companyInfo)
        } catch (e: Exception) {
            Log.e(TAG, "insertCompanyInfo failed with exception: $e")
        }
    }


    companion object {
        const val TAG = "CompanyInfoProvider"
    }
}
