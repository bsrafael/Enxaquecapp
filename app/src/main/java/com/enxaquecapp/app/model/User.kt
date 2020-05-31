package com.enxaquecapp.app.model

import java.util.*

data class User(
    var name: String,
    var email: String,
    var birth: Date,
    var age: Int,
    var gender: Char,
    var token: String = "",
    var cases: MutableList<Case> = mutableListOf()
)

