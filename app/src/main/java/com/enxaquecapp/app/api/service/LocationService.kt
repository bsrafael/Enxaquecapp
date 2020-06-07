package com.enxaquecapp.app.api.service

import android.location.Location
import retrofit2.Call
import retrofit2.http.GET

interface LocationService {

    @GET("locals")
    fun getEpisodes(): Call<List<Location>>
}