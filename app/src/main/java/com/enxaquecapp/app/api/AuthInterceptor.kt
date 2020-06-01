package com.enxaquecapp.app.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var builder = chain.request().newBuilder()

        if (!token.isNullOrEmpty()) {
            builder = builder.addHeader("Authorization", "bearer $token")
        }

        val request = builder.build()
        return chain.proceed(request)
    }
}