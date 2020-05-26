package com.enxaquecapp.app.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.model.User
import java.util.*

class UserRepository {

    var authenticationState = MutableLiveData<AuthenticationState>()
    var authState = AuthenticationState.UNAUTHENTICATED
    lateinit var user: User


    init {
        // In this example, the user is always unauthenticated when MainActivity is launched
//        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

    fun refuseAuthentication() {
        authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
    }

    fun authenticate(email: String, password: String) {
        Log.i("UserRepository", "autenticando")
        if (passwordIsValidForEmail(email, password)) {
            user = getUser(email)
            authenticationState.value = AuthenticationState.AUTHENTICATED
            Log.i("UserRepository", "user autenticado")
        } else {
            authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
            Log.i("UserRepository", "autenticação inválida")
        }
    }

    /** TODO [API]: Link to Backend **/
    private fun passwordIsValidForEmail(email: String, password: String): Boolean {
        return true
    }

    /** TODO [API]: Link to Backend **/
    private fun getUser(email: String): User {
        return User(
            "Sample Name",
            email,
            Date(),
            21,
            'M',
            "s4mpl3t0kEn"
        )
    }

}