package com.enxaquecapp.app.api.service

import com.enxaquecapp.app.model.Episode
import retrofit2.Call
import retrofit2.http.GET

interface EpisodeService {

    @GET("episodes")
    fun getEpisodes(): Call<List<Episode>>
}