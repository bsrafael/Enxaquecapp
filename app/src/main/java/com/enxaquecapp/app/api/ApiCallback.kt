package com.enxaquecapp.app.api

import android.util.Log

interface ApiCallback<T> {

    fun success(response: T)

    fun noContent() { Log.i("ApiCallback", "no content") }

    fun failure(errorCode: Int, message: String)

    fun error()
}