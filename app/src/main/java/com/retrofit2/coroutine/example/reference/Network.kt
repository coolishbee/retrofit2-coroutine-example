package com.retrofit2.coroutine.example.reference

import com.retrofit2.coroutine.example.http.respBody.RespCarrier
import com.retrofit2.coroutine.example.http.respBody.RespCarrierTracks
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

object Network {

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

    fun <T> getListCarrier(onSuccess: ((List<RespCarrier>)->Unit)?,
                          onError: ((Throwable) -> Unit)?)
    {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                onSuccess?.invoke(ApiProvider.provideApi().getCarriers())
            }catch (httpException: HttpException){
                defaultError(httpException)
            }catch (t: Throwable){
                onError?.let {
                    onError(t)
                }
            }
        }
    }

    fun <T> getCarrierTracks(onSuccess: ((RespCarrierTracks) -> Unit)?,
                         onError: ((Throwable) -> Unit)?)
    {
        val trackId = 1111111111111L

        GlobalScope.launch(Dispatchers.Main) {
            try {
                onSuccess?.invoke(ApiProvider.provideApi().getCarriersTracks("kr.epost", trackId))
            }catch (httpException: HttpException){
                defaultError(httpException)
            }catch (t: Throwable){
                onError?.let {
                    onError(t)
                }
            }
        }
    }

}