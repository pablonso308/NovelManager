package com.example.novelmanager.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {
    @GET("data")
    fun getData(): Call<List<Data>>

    @POST("data")
    fun postData(@Body data: Data): Call<Void>
}