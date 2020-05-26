package com.enxaquecapp.app.model

import java.util.*

data class User(
    val name: String,
    val email: String,
    val birth: Date,
    val age: Int,
    val gender: Char
)
