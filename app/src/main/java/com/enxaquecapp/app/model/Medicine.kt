package com.enxaquecapp.app.model

import java.util.*

data class Medicine(
    val id: UUID,
    val name: String,
    val description: String,
    val start: Date,
    val hourInterval: String,
    val totalDoses: Int,
    val consumedDoses: Int,
    val isActive: Boolean
)