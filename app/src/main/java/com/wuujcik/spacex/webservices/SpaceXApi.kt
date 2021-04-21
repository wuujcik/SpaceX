package com.wuujcik.spacex.webservices

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.spacexdata.com/v4/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

object SpaceXApi {
    val retrofitService : SpaceXApiService by lazy { retrofit.create(SpaceXApiService::class.java) }
}
