package com.retrofit2.coroutine.example.http.respBody

data class RespLogin(
    val code: Int,
    val msg: String,
    val data: RespLoginInfo
)

data class RespLoginInfo(
    val player: String
)