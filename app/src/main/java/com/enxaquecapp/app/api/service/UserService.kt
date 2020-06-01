package com.enxaquecapp.app.api.service

import com.enxaquecapp.app.api.models.input.UserInputModel
import com.enxaquecapp.app.api.models.view.TokenViewModel
import com.enxaquecapp.app.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @GET("users")
    fun getCurrent(): Call<User>

    @POST("users")
    fun register(@Body im: UserInputModel): Call<TokenViewModel>
}