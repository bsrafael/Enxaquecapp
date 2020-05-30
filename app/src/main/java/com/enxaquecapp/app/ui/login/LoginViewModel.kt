package com.enxaquecapp.app.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.repository.UserRepository
import com.enxaquecapp.app.shared.State

class LoginViewModel : ViewModel() {

    var userRepository: UserRepository = UserRepository()

    init {
    }

    fun refuseAuthentication() {
        userRepository.refuseAuthentication()
    }

    fun authenticate(email: String, password: String): AuthenticationState {
        Log.i("LoginViewModel", "autenticando")
        return userRepository.authenticate(email, password)
    }

}
