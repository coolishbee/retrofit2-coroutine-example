package com.retrofit2.coroutine.example.reference

import android.util.Log
import com.retrofit2.coroutine.example.http.reqBody.ReqLogin
import com.retrofit2.coroutine.example.http.respBody.RespCarrier
import com.retrofit2.coroutine.example.http.respBody.RespCarrierTracks
import com.retrofit2.coroutine.example.http.respBody.RespLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Network {

    fun getListCarrier(onSuccess: ((List<RespCarrier>)->Unit)?,
                       onError: ((Throwable) -> Unit)?,
                       doOnSubscribe: (()-> Unit)?= null,
                       doOnTerminate: (()-> Unit)?= null
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            doOnSubscribe?.invoke()
            try {
                onSuccess?.invoke(ApiProvider.provideApi().getCarriers())
            }catch (t: Throwable){
                onError?.let {
                    onError(t)
                }
            }finally {
                doOnTerminate?.invoke()
            }
        }
    }

    fun getCarrierTracks(onSuccess: ((RespCarrierTracks) -> Unit)?,
                         onError: ((Throwable) -> Unit)?)
    {
        val trackId = 1111111111111L

        GlobalScope.launch(Dispatchers.Main) {
            try {
                onSuccess?.invoke(ApiProvider.provideApi().getCarriersTracks("kr.epost", trackId))
            }catch (t: Throwable){
                onError?.let {
                    onError(t)
                }
            }
        }
    }

    fun login(reqBody: ReqLogin,
              onSuccess: ((RespLogin) -> Unit)?,
              onError: ((Throwable) -> Unit)?)
    {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiProvider.provideApi().login(reqBody)
                if(response.isSuccessful){
                    response.body()?.let {
                        onSuccess?.invoke(it)
                    }
                }else{
                    response.errorBody()?.string()?.let { Log.d("Network", it) }
                    Log.d("Network", response.message())
                }
            }catch (t: Throwable){
                onError?.let {
                    onError(t)
                }
            }
        }
    }

}