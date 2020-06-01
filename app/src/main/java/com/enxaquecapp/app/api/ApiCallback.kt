package com.enxaquecapp.app.api

interface ApiCallback<T> {

    fun success(response: T)

    fun error()
}