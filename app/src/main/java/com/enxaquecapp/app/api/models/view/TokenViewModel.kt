package com.enxaquecapp.app.api.models.view

import com.enxaquecapp.app.model.User

data class TokenViewModel(
    val token: String,
    val user: User
)