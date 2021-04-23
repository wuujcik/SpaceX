package com.wuujcik.spacex.webservices

import com.wuujcik.spacex.persistence.CompanyInfo
import com.wuujcik.spacex.persistence.Launch
import retrofit2.Call
import retrofit2.http.GET

interface SpaceXApiService {

    @GET("company")
    fun getCompanyInfo(): Call<CompanyInfo>

    @GET("launches/upcoming")
    fun getUpcomingLaunches(): Call<List<Launch?>>

    @GET("launches/past")
    fun getPastLaunches(): Call<List<Launch?>>
}
