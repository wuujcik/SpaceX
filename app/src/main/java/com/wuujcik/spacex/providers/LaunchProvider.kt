package com.wuujcik.spacex.providers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wuujcik.spacex.persistence.Launch
import com.wuujcik.spacex.persistence.Rocket
import com.wuujcik.spacex.webservices.SpaceXApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class LaunchProvider {

    // The internal MutableLiveData Launch that stores the most recent response
    private val _launches = MutableLiveData<List<Launch?>?>()
    private val _rocket = MutableLiveData<Rocket?>()

    // The external immutable LiveData for the response CompanyInfo
    val launches: LiveData<List<Launch?>?>
        get() = _launches
    val rocket: LiveData<Rocket?>
        get() = _rocket


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
            query: String?
    ) {
        query ?: return
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

    fun getRocket(scope: CoroutineScope, rocketId: String?) {
        rocketId ?: return
        scope.launch {
            try {
                val rocket: Rocket? =
                        SpaceXApi.retrofitService.getOneRocket(rocketId)
                                .awaitResponse()
                                .body()
                _rocket.value = rocket
            } catch (e: Exception) {
                _rocket.value = null
                Log.e(TAG, "getRocket failed with exception: $e")
            }
        }
    }

    companion object {
        const val TAG = "LaunchProvider"
    }
}
