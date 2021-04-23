package com.wuujcik.spacex.providers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wuujcik.spacex.persistence.CompanyInfo
import com.wuujcik.spacex.webservices.SpaceXApi.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CompanyInfoProvider {

    // The internal MutableLiveData CompanyInfo that stores the most recent response
    private val _response = MutableLiveData<CompanyInfo?>()

    // The external immutable LiveData for the response CompanyInfo
    val companyInfo: LiveData<CompanyInfo?>
        get() = _response

    fun getCompanyInfo(
        scope: CoroutineScope
    ) {
        scope.launch {
            try {
                val companyInfo: CompanyInfo? =
                    retrofitService.getCompanyInfo()
                        .awaitResponse()
                        .body()
                _response.value = companyInfo
            } catch (e: Exception) {
                _response.value = null
                Log.e(LaunchProvider.TAG, "getCompanyInfo failed with exception: $e")
            }
        }
    }
    companion object {
        const val TAG = "CompanyInfoProvider"
    }
}
