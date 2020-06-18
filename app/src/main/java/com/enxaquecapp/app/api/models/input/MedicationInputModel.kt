package com.enxaquecapp.app.api.models.input

import java.util.*

data class MedicationInputModel(
    val name: String,
    val description: String,
    val start: Date,
    val interval: String,
    val totalDoses: Int
)