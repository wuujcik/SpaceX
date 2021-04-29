package com.wuujcik.spacex.persistence.launch


import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.wuujcik.spacex.providers.LaunchProvider
import com.wuujcik.spacex.webservices.SpaceXApi
import kotlinx.coroutines.CoroutineScope
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LaunchesDataSource(val scope: CoroutineScope, val query: JSONObject? = null) :
    PageKeyedDataSource<Int, Launch>() {


    private val reqBodyArgs = JSONObject()



//    val datesUtsFilter = JSONObject().put("\$gte", startDate.toIsoDate()).put(
//        "\$lte",
//        endDate.toIsoDate()
//    )
//    val args = JSONObject().put("date_utc", datesUtsFilter)
//    val query = JSONObject().put("query", args)


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Launch>
    ) {
//        if (context.isInternetAvailable()) {
        getLaunches(callback)
//        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Launch>) {
//        if (context.isInternetAvailable()) {
        val nextPageNo = params.key + 1
        getMoreLaunches(params.key, nextPageNo, callback)
//        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Launch>) {
//        if (context.isInternetAvailable()) {
        val previousPageNo = if (params.key > 1) params.key - 1 else 0
        getMoreLaunches(params.key, previousPageNo, callback)
//        }
    }

    private fun getLaunches(callback: LoadInitialCallback<Int, Launch>) {

//        context.showProgressBar()
        val pageArgs = JSONObject().put(KEY_PAGE, 1)
        reqBodyArgs.put(KEY_OPTIONS, pageArgs).put(KEY_QUERY, query)
        val reqBody = reqBodyArgs.toString().toRequestBody("application/json".toMediaTypeOrNull())
        SpaceXApi.retrofitService.getFilteredLaunches(reqBody).enqueue(object :
            Callback<FilteredLaunches?> {

            override fun onFailure(call: Call<FilteredLaunches?>, t: Throwable) {
                //  Utility.hideProgressBar()
                Log.e(LaunchProvider.TAG, "getFilteredLaunches $call failed with $t")
            }

            override fun onResponse(
                call: Call<FilteredLaunches?>,
                response: Response<FilteredLaunches?>
            ) {
                //  Utility.hideProgressBar()
                val list = response.body()?.docs?.toList()
                Log.e(LaunchProvider.TAG, "LaunchProvider getFilteredLaunches list ${list}")

                val launchResponse = response.body()
                val listOfLaunches = launchResponse?.docs
                listOfLaunches?.let { callback.onResult(it, null, 2) }
            }
        })
    }

    private fun getMoreLaunches(
        pageNo: Int,
        previousOrNextPageNo: Int,
        callback: LoadCallback<Int, Launch>
    ) {

        val pageArgs = JSONObject().put(KEY_PAGE, pageNo)
        reqBodyArgs.put(KEY_OPTIONS, pageArgs).put(KEY_QUERY, query)
        val reqBody = reqBodyArgs.toString().toRequestBody("application/json".toMediaTypeOrNull())
        SpaceXApi.retrofitService.getFilteredLaunches(reqBody)
            .enqueue(object : Callback<FilteredLaunches?> {
                override fun onFailure(call: Call<FilteredLaunches?>, t: Throwable) {
                    //  Utility.hideProgressBar()
                    Log.e(LaunchProvider.TAG, "getFilteredLaunches $call failed with $t")
                }

                override fun onResponse(
                    call: Call<FilteredLaunches?>,
                    response: Response<FilteredLaunches?>
                ) {
                    //  Utility.hideProgressBar()
                    val launchResponse = response.body()
                    val listOfLaunches = launchResponse?.docs
                    listOfLaunches?.let { callback.onResult(it, previousOrNextPageNo) }
                }
            })
    }


    inner class LaunchesDataSourceFactory : DataSource.Factory<Int, Launch>() {
        override fun create(): DataSource<Int, Launch> = LaunchesDataSource(scope)
    }


    companion object {
        const val KEY_PAGE = "page"
        const val KEY_OPTIONS = "options"
        const val KEY_QUERY = "query"
    }
}
