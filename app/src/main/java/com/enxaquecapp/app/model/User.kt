package com.enxaquecapp.app.model

import java.util.*

data class User(
    var id: UUID,
    var name: String,
    var email: String,
    var birthDate: Date,
    var age: Int,
    var gender: String
)

