package com.enxaquecapp.app.api.service

import com.enxaquecapp.app.model.Relief
import retrofit2.Call
import retrofit2.http.GET

interface ReliefService {

    @GET("reliefs")
    fun getReliefs(): Call<List<Relief>>
}