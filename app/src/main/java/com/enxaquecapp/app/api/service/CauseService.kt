package com.enxaquecapp.app.api.service

import com.enxaquecapp.app.model.Cause
import retrofit2.Call
import retrofit2.http.GET

interface CauseService {

    @GET("causes")
    fun getCauses(): Call<List<Cause>>
}