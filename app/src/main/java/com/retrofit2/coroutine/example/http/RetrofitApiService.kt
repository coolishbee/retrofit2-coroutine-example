package com.retrofit2.coroutine.example.http

import com.retrofit2.coroutine.example.http.respBody.RespCarrier
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitApiService {

    @GET("/carriers")
    suspend fun getCarriers(): List<RespCarrier>

}