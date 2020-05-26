package com.enxaquecapp.app.model

import java.util.*

data class Medicine(
    val name: String,
    val description: String,
    val start: Date,
    val hourInterval: Int,
    val totalDoses: Int
)