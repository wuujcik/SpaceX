package com.wuujcik.spacex.providers

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wuujcik.spacex.persistence.Rocket
import com.wuujcik.spacex.webservices.SpaceXApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Request
import okio.Buffer
import okio.IOException
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


    //TODO: remove this fun
    private fun bodyToString(request: Request): String? {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body?.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }

    companion object {
        const val TAG = "LaunchProvider"
    }
}
