package com.enxaquecapp.app.enums

enum class AuthenticationState {
    UNAUTHENTICATED,        // Initial state, the user needs to authenticate
    AUTHENTICATED  ,        // The user has authenticated successfully
    INVALID_AUTHENTICATION  // Authentication failed
}