package com.enxaquecapp.app.model

import java.util.*

data class Case (
    val startDate: Date? = null,
    val startTime: String? = null,
    val endDate: Date? = null,
    val endTime: String? = null,
    val intensity: Int? = null,
    val location: Location? = null,
    val cause: Cause? = null,
    val relief: Relief? = null,
    val helped: Boolean? = null )