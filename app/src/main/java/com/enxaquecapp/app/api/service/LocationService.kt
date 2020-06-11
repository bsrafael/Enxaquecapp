package com.enxaquecapp.app.api.service

import com.enxaquecapp.app.model.Location
import retrofit2.Call
import retrofit2.http.GET

interface LocationService {

    @GET("locals")
    fun getLocations(): Call<List<Location>>
}