package com.enxaquecapp.app.api.models.input

import java.util.*

data class UserPatchInputModel(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val birthDate: Date? = null,
    val gender: String? = null
)