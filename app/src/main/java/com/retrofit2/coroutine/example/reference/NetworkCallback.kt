package com.retrofit2.coroutine.example.reference

class NetworkCallback<T> {
    var success: ((T) -> Unit) ?= null
    var error: ((Throwable)-> Unit) ?= null
}