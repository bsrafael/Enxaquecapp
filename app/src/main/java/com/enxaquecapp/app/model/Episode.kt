package com.enxaquecapp.app.model

import java.util.*

data class Episode(
    val id: UUID? = null,
    val start: Date? = null,
    val end: Date? = null,
    val intensity: Int? = null,
    val location: Location? = null,
    val cause: Cause? = null,
    val relief: Relief? = null,
    val reliefWorked: Boolean? = null
)