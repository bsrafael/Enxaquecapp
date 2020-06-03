package com.enxaquecapp.app.api

interface ApiCallback<T> {

    fun success(response: T)

    fun failure(errorCode: Int, message: String)

    fun error()
}