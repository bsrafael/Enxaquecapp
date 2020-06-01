package com.enxaquecapp.app.api

import com.enxaquecapp.app.api.service.AutheticationService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientFactory {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://exaquecapp.herokuapp.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun authenticationService(): AutheticationService = retrofit.create(AutheticationService::class.java)
}