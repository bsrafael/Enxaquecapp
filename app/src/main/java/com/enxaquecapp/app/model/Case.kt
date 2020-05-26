package com.enxaquecapp.app.model

import java.util.*

data class Case (
    val start: Date,
    val end: Date,
    val intensity: Int,
    val location: Location,
    val cause: Cause,
    val relief: Relief,
    val helped: Boolean )