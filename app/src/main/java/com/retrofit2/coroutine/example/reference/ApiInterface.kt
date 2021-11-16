package com.retrofit2.coroutine.example.reference

import com.retrofit2.coroutine.example.http.respBody.RespCarrier
import com.retrofit2.coroutine.example.http.respBody.RespCarrierTracks
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/carriers")
    fun getCarriersAsync(): Deferred<List<RespCarrier>>

    @GET("/carriers")
    suspend fun getCarriers(): List<RespCarrier>

    @GET("/carriers/{carrier_id}/tracks/{track_id}")
    suspend fun getCarriersTracks(@Path("carrier_id") carrierId: String,
                                  @Path("track_id") trackId: Long) : RespCarrierTracks
}