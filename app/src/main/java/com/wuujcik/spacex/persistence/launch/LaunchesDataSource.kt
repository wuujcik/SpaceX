package com.wuujcik.spacex.persistence.launch


import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.wuujcik.spacex.webservices.SpaceXApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LaunchesDataSource(private val queryArgs: JSONObject, private val scope: CoroutineScope) :
    PageKeyedDataSource<Int, Launch>() {

    private val reqBodyArgs = JSONObject()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Launch>
    ) {
        scope.launch {
            getLaunches(callback)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Launch>) {
        scope.launch {
            val nextPageNo = params.key + 1
            getMoreLaunches(params.key, nextPageNo, callback)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Launch>) {
        scope.launch {
            val previousPageNo = if (params.key > 1) params.key - 1 else 0
            getMoreLaunches(params.key, previousPageNo, callback)
        }
    }

    private fun getLaunches(callback: LoadInitialCallback<Int, Launch>) {

        scope.launch {
            val pageArgs = JSONObject().put(KEY_PAGE, 1)
            reqBodyArgs.put(KEY_OPTIONS, pageArgs).put(KEY_QUERY, queryArgs)
            val reqBody =
                reqBodyArgs.toString().toRequestBody("application/json".toMediaTypeOrNull())

            SpaceXApi.retrofitService.getFilteredLaunches(reqBody).enqueue(object :
                Callback<FilteredLaunches?> {

                override fun onFailure(call: Call<FilteredLaunches?>, t: Throwable) {
                    Log.e(TAG, "LaunchesDataSource $call failed with $t")
                }

                override fun onResponse(
                    call: Call<FilteredLaunches?>,
                    response: Response<FilteredLaunches?>
                ) {
                    val launchResponse = response.body()
                    val listOfLaunches = launchResponse?.docs
                    listOfLaunches?.let { callback.onResult(it, null, 2) }
                }
            })
        }
    }

    private fun getMoreLaunches(
        pageNo: Int,
        previousOrNextPageNo: Int,
        callback: LoadCallback<Int, Launch>
    ) {
        scope.launch {
            val pageArgs = JSONObject().put(KEY_PAGE, pageNo)
            reqBodyArgs.put(KEY_OPTIONS, pageArgs).put(KEY_QUERY, queryArgs)
            val reqBody =
                reqBodyArgs.toString().toRequestBody("application/json".toMediaTypeOrNull())
            SpaceXApi.retrofitService.getFilteredLaunches(reqBody)
                .enqueue(object : Callback<FilteredLaunches?> {
                    override fun onFailure(call: Call<FilteredLaunches?>, t: Throwable) {
                        Log.e(TAG, "getFilteredLaunches $call failed with $t")
                    }

                    override fun onResponse(
                        call: Call<FilteredLaunches?>,
                        response: Response<FilteredLaunches?>
                    ) {
                        val launchResponse = response.body()
                        val listOfLaunches = launchResponse?.docs
                        listOfLaunches?.let { callback.onResult(it, previousOrNextPageNo) }
                    }
                })
        }
    }

    companion object {
        const val TAG = "LaunchesDataSource"
        const val KEY_PAGE = "page"
        const val KEY_OPTIONS = "options"
        const val KEY_QUERY = "query"
    }
}

class LaunchesDataSourceFactory(private val queryArgs: JSONObject, private val scope: CoroutineScope) : DataSource.Factory<Int, Launch>() {
    override fun create(): DataSource<Int, Launch> {
     return   LaunchesDataSource(queryArgs, scope)
    }
}