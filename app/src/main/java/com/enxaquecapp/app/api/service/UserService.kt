package com.enxaquecapp.app.api.service

import com.enxaquecapp.app.api.models.input.UserInputModel
import com.enxaquecapp.app.api.models.input.UserPatchInputModel
import com.enxaquecapp.app.api.models.view.TokenViewModel
import com.enxaquecapp.app.model.User
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface UserService {

    @GET("users")
    fun getCurrent(): Call<User>

    @POST("users")
    fun register(@Body im: UserInputModel): Call<TokenViewModel>

    @PATCH("users/{id}")
    fun update(@Path("id") id: UUID, im: UserPatchInputModel): Call<User>
}