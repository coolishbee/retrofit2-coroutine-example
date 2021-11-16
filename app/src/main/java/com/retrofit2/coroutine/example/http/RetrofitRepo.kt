package com.retrofit2.coroutine.example.http

import com.retrofit2.coroutine.example.http.RetrofitClient.apiService

class RetrofitRepo {


    suspend fun getCarriers() = apiService.getCarriers()
}