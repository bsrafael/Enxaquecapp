package com.enxaquecapp.app.api.models.input

import java.util.*

data class EpisodeInputModel(
    val start: Date,
    val end: Date?,
    val intensity: Int,
    val releafWorked: Boolean, // TODO(Julio) correct typo on backend
    val localId: UUID?,
    val causeId: UUID?,
    val reliefId: UUID?
)