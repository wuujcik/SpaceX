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
    private val _upcomingLaunches = MutableLiveData<List<Launch?>?>()

    // The external immutable LiveData for the response CompanyInfo
    val upcomingLaunches: LiveData<List<Launch?>?>
        get() = _upcomingLaunches


    fun getUpcomingLaunches(
        scope: CoroutineScope
    ) {
        scope.launch {
            try {
                val upcomingLaunches: List<Launch?>? =
                    SpaceXApi.retrofitService.getUpcomingLaunches()
                        .awaitResponse()
                        .body()
                _upcomingLaunches.value = upcomingLaunches
            } catch (e: Exception) {
                _upcomingLaunches.value = null
                Log.e(TAG, "getUpcomingLaunches failed with exception: $e")
            }
        }
    }

    companion object {
        const val TAG = "LaunchProvider"
    }
}
