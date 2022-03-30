package com.retrofit2.coroutine.example.reference

import com.retrofit2.coroutine.example.http.reqBody.ReqLogin
import com.retrofit2.coroutine.example.http.respBody.RespCarrier
import com.retrofit2.coroutine.example.http.respBody.RespCarrierTracks
import com.retrofit2.coroutine.example.http.respBody.RespLogin
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

object Network {

    //use Deferred
    //start
    fun <T> request(
        call: Deferred<T>,
        success: ((response: T)-> Unit)?,
        error: ((throwable: Throwable)-> Unit)?= null,
        doOnSubscribe: (()-> Unit)?= null,
        doOnTerminate: (()-> Unit)?= null) {
        GlobalScope.launch(Dispatchers.Main) {
            doOnSubscribe?.invoke()
            try {
                success?.invoke(call.await())
            } catch (t: Throwable) {
                error?.invoke(t)
            } finally {
                doOnTerminate?.invoke()
            }
        }
    }
    //end

    //use suspend
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
                onSuccess?.invoke(ApiProvider.provideApi().login(reqBody))
            }catch (t: Throwable){
                onError?.let {
                    onError(t)
                }
            }
        }
    }

}