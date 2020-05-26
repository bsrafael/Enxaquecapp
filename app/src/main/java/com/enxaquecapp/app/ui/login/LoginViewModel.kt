package com.enxaquecapp.app.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.repository.UserRepository

class LoginViewModel : ViewModel() {

    val authenticationState = MutableLiveData<AuthenticationState>()
    var userRepository: UserRepository = UserRepository()

    init {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
        userRepository.authenticationState.observeForever {
            authenticationState.postValue(it)
        }
    }

    fun refuseAuthentication() {
        userRepository.refuseAuthentication()
    }

    fun authenticate(email: String, password: String) {
        Log.i("LoginViewModel", "autenticando")
        userRepository.authenticate(email, password)
    }

}
