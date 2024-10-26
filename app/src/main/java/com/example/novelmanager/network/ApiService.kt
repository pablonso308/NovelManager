package com.example.novelmanager.network

import com.example.novelmanager.database.entidades.Novel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {
    @GET("novels")
    fun getNovels(): Call<List<Novel>>

    @POST("novels")
    fun postNovel(@Body novel: Novel): Call<Void>
}