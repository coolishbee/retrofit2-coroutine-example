package com.retrofit2.coroutine.example.reference

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {
    private const val BASE_URL = "https://apis.tracker.delivery"

    private val okHttpClient: OkHttpClient

    init {
        val httpLogging = provideLoggingInterceptor()
        okHttpClient = provideOkHttpClient(httpLogging)
    }

    fun provideApi(): ApiInterface
            = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(ApiInterface::class.java)

    private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient
            = OkHttpClient().newBuilder()
        .run {
            addInterceptor(interceptor)
        }.build()

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor
            = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}