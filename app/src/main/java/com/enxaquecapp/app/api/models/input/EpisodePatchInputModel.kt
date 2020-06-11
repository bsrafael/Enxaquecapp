package com.enxaquecapp.app.api.models.input

import java.util.*

data class EpisodePatchInputModel(
    val start: Date? = null,
    val end: Date? = null,
    val clearEnd: Boolean = false,
    val intensity: Int? = null,
    val releafWorked: Boolean? = null, // TODO(Julio) fix typo on backend
    val localId: UUID? = null,
    val clearLocal: Boolean = false,
    val causeId: UUID? = null,
    val clearCause: Boolean = false,
    val reliefId: UUID? = null,
    val clearRelief: Boolean = false
)