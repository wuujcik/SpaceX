package com.wuujcik.spacex.providers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wuujcik.spacex.persistence.Launch
import com.wuujcik.spacex.webservices.SpaceXApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class LaunchProvider {

    // The internal MutableLiveData Launch that stores the most recent response
    private val _launches = MutableLiveData<List<Launch?>?>()

    // The external immutable LiveData for the response CompanyInfo
    val launches: LiveData<List<Launch?>?>
        get() = _launches


    fun getLaunches(
        scope: CoroutineScope
    ) {
        scope.launch {
            try {
                val listOfLaunches: List<Launch?>? =
                    SpaceXApi.retrofitService.getAllLaunches()
                        .awaitResponse()
                        .body()
                _launches.value = listOfLaunches
            } catch (e: Exception) {
                _launches.value = null
                Log.e(TAG, "getLaunches failed with exception: $e")
            }
        }
    }

    fun getFilteredLaunches(
            scope: CoroutineScope,
            query: String
    ) {
        scope.launch {
            try {
                val listOfLaunches: List<Launch?>? =
                        SpaceXApi.retrofitService.getFilteredLaunches(query)
                                .awaitResponse()
                                .body()
                _launches.value = listOfLaunches
            } catch (e: Exception) {
                _launches.value = null
                Log.e(TAG, "getFilteredLaunches failed with exception: $e")
            }
        }
    }

    companion object {
        const val TAG = "LaunchProvider"
    }
}
