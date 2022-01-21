package com.retrofit2.coroutine.example.reference

import com.retrofit2.coroutine.example.http.respBody.RespCarrier
import com.retrofit2.coroutine.example.http.respBody.RespCarrierTracks
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

object Network {

    //v1
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

    //v2
    //start
    private fun defaultError(t: Throwable) {
        t.printStackTrace()
    }

    fun <T> request(call: Deferred<T>,
                    callback: NetworkCallback<T>) {
        request(call, callback.success, callback.error)
    }

    private fun <T> request(call: Deferred<T>,
                            onSuccess: ((T) -> Unit)?,
                            onError: ((Throwable) -> Unit)?)
    {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                onSuccess?.let {
                    onSuccess(call.await())
                }
            } catch (httpException: HttpException) {
                // a non-2XX response was received
                defaultError(httpException)
            } catch (t: Throwable) {
                // a networking or data conversion error
                onError?.let {
                    onError(t)
                }
            }
        }
    }
    //end

    //v3
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

    //v3
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

}