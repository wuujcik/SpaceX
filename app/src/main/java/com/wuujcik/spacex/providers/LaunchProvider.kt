package com.wuujcik.spacex.providers

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wuujcik.spacex.persistence.Rocket
import com.wuujcik.spacex.persistence.launch.FilteredLaunches
import com.wuujcik.spacex.persistence.launch.Launch
import com.wuujcik.spacex.persistence.launch.LaunchesDataSource
import com.wuujcik.spacex.webservices.SpaceXApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse


class LaunchProvider(val context: Context) {

    private val _rocket = MutableLiveData<Rocket?>()
    val rocket: LiveData<Rocket?>
        get() = _rocket

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

    fun getLaunches(
        scope: CoroutineScope,
        sort: Int,
        callback: (success: Launch?) -> Unit
    ) {

        scope.launch {
            val reqBodyArgs = JSONObject()
            val sortByDateUnix = JSONObject().put(KEY_DATE_UNIX, sort)
            val sortArgs = JSONObject().put(KEY_SORT, sortByDateUnix).put(KEY_LIMIT, 1)
            reqBodyArgs.put(KEY_OPTIONS, sortArgs)
            val reqBody =
                reqBodyArgs.toString().toRequestBody("application/json".toMediaTypeOrNull())
            SpaceXApi.retrofitService.getFilteredLaunches(reqBody).enqueue(object :
                Callback<FilteredLaunches?> {

                override fun onFailure(call: Call<FilteredLaunches?>, t: Throwable) {
                    Log.e(TAG, "getLaunches $call failed with $t")
                }

                override fun onResponse(
                    call: Call<FilteredLaunches?>,
                    response: Response<FilteredLaunches?>
                ) {
                    val launchResponse = response.body()
                    val listOfLaunches = launchResponse?.docs
                    callback(listOfLaunches?.get(0))
                }
            })
        }
    }

    companion object {
        const val TAG = "LaunchProvider"
        const val KEY_SORT = "sort"
        const val KEY_DATE_UNIX = "date_unix"
        const val KEY_LIMIT = "limit"
        const val KEY_OPTIONS = "options"
        const val SORT_DESC = -1
        const val SORT_ASC = 1
    }
}
