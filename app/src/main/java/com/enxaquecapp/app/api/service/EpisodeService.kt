package com.enxaquecapp.app.api.service

import com.enxaquecapp.app.api.models.input.EpisodeInputModel
import com.enxaquecapp.app.api.models.input.EpisodePatchInputModel
import com.enxaquecapp.app.model.Episode
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface EpisodeService {

    @GET("episodes")
    fun getEpisodes(): Call<List<Episode>>

    @POST("episodes")
    fun create(@Body im: EpisodeInputModel): Call<Episode>

    @PATCH("episodes/{id}")
    fun update(@Path("id") id: UUID, @Body im: EpisodePatchInputModel): Call<Episode>

    @DELETE("episodes/{id}")
    fun delete(@Path("id") id: UUID): Call<Void>
}