package com.wuujcik.spacex.webservices

import com.wuujcik.spacex.persistence.CompanyInfo
import com.wuujcik.spacex.persistence.Launch
import com.wuujcik.spacex.persistence.Rocket
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpaceXApiService {

    @GET("company")
    fun getCompanyInfo(): Call<CompanyInfo>

    @GET("launches")
    fun getAllLaunches(): Call<List<Launch?>?>

    @GET("launches")
    fun getFilteredLaunches(@Query("query") filter: String): Call<List<Launch?>?>

    @GET("rockets/{id}")
    fun getOneRocket(@Path("id") taskId: String): Call<Rocket?>

}
