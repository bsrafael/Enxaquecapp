package com.enxaquecapp.app.api

import com.enxaquecapp.app.api.service.AutheticationService
import com.enxaquecapp.app.api.service.UserService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientFactory(token: String? = null) {

    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create();

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(token))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://exaquecapp.herokuapp.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    fun authenticationService(): AutheticationService = retrofit.create(AutheticationService::class.java)
    fun userService(): UserService = retrofit.create(UserService::class.java)
}