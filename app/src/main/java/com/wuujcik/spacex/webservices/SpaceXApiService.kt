package com.wuujcik.spacex.webservices

import com.wuujcik.spacex.persistence.companyInfo.CompanyInfo
import retrofit2.Call
import retrofit2.http.GET

interface SpaceXApiService {

    @GET("company")
    fun getCompanyInfo(): Call<CompanyInfo>
}
