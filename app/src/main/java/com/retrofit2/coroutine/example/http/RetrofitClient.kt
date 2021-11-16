package com.retrofit2.coroutine.example.http

import androidx.lifecycle.MutableLiveData
import com.retrofit2.coroutine.example.http.respBody.RespCarrier
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
//    private val retrofitClient: Retrofit.Builder by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://apis.tracker.delivery")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(RetrofitUtils.unsafeOkHttpClient.build())
//    }
//    val apiService: RetrofitApiService by lazy {
//        retrofitClient.build().create(RetrofitApiService::class.java)
//    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://apis.tracker.delivery")
            .client(RetrofitUtils.unsafeOkHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: RetrofitApiService = getRetrofit().create(RetrofitApiService::class.java)

}