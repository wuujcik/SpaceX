package com.wuujcik.spacex.webservices

import com.wuujcik.spacex.persistence.companyInfo.CompanyInfo
import com.wuujcik.spacex.persistence.launch.Launch
import com.wuujcik.spacex.persistence.Rocket
import com.wuujcik.spacex.persistence.launch.FilteredLaunches
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface SpaceXApiService {

    @GET("company")
    fun getCompanyInfo(): Call<CompanyInfo>

    @GET("launches")
    fun getAllLaunches(): Call<List<Launch>?>

    @Headers("Content-Type: application/json")
    @POST("launches/query")
    fun getFilteredLaunches(@Body requestBody: RequestBody): Call<FilteredLaunches?>

    @GET("rockets/{id}")
    fun getOneRocket(@Path("id") taskId: String): Call<Rocket?>

}
