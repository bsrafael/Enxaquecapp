package com.enxaquecapp.app.api.service

import com.enxaquecapp.app.api.models.input.TokenInputModel
import com.enxaquecapp.app.api.models.view.TokenViewModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AutheticationService {

    @GET("tokens/authenticate")
    fun authenticate(): Call<Void>

    @POST("tokens")
    fun authenticate(@Body im: TokenInputModel): Call<TokenViewModel>
}