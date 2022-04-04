package com.retrofit2.coroutine.example.reference

import com.retrofit2.coroutine.example.http.reqBody.ReqLogin
import com.retrofit2.coroutine.example.http.respBody.RespCarrier
import com.retrofit2.coroutine.example.http.respBody.RespCarrierTracks
import com.retrofit2.coroutine.example.http.respBody.RespLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {

    @GET("/carriers")
    suspend fun getCarriers(): List<RespCarrier>

    @GET("/carriers/{carrier_id}/tracks/{track_id}")
    suspend fun getCarriersTracks(@Path("carrier_id") carrierId: String,
                                  @Path("track_id") trackId: Long) : RespCarrierTracks

    @POST("/auth")
    suspend fun login(@Body data: ReqLogin): Response<RespLogin>

}