package com.enxaquecapp.app.model

import java.util.*

data class Medicine(
    var id: Int? = null,
    val name: String,
    val start: Date,
    val hourInterval: Interval
)