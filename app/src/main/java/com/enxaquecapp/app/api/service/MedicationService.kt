package com.enxaquecapp.app.api.service

import com.enxaquecapp.app.api.models.input.MedicationInputModel
import com.enxaquecapp.app.model.Medicine
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface MedicationService {

    @GET("medications")
    fun get(): Call<List<Medicine>>

    @POST("medications")
    fun create(@Body im: MedicationInputModel): Call<Medicine>

    @PATCH("medications/{id}")
    fun update(@Path("id") id: UUID, @Body im: MedicationInputModel): Call<Medicine>

    @DELETE("medications/{id}")
    fun delete(@Path("id") id: UUID): Call<Void>

    @POST("medications/{id}/finish")
    fun finish(@Path("id") id: UUID): Call<Medicine>
}