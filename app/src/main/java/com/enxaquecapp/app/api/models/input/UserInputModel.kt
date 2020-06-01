package com.enxaquecapp.app.api.models.input

import java.util.*

data class UserInputModel(
    val name: String,
    val email: String,
    val password: String,
    val birthDate: Date,
    val gender: String
)