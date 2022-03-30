package com.retrofit2.coroutine.example.http.reqBody

data class ReqLogin(
    val login_type: String,
    val login_token: String
){
    companion object{
        fun createReqLoginInfo(type: String, token: String): ReqLogin {
            return ReqLogin(
                type,
                token
            )
        }
    }
}