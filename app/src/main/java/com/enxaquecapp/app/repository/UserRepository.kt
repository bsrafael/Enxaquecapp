package com.enxaquecapp.app.repository

import android.util.Log
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.model.Case
import com.enxaquecapp.app.model.User
import com.enxaquecapp.app.shared.State
import java.util.*

class UserRepository {


    init {
    }

    fun refuseAuthentication(): AuthenticationState {
        State.authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
        return AuthenticationState.UNAUTHENTICATED
    }

    fun authenticate(email: String, password: String): AuthenticationState {
        Log.i("UserRepository", "autenticando")
        return if (passwordIsValidForEmail(email, password)) {
            State.user.postValue( fetchUser(email) )
            State.authenticationState.postValue( AuthenticationState.AUTHENTICATED )
            Log.i("UserRepository", "user autenticado")
            AuthenticationState.AUTHENTICATED
        } else {
            Log.i("UserRepository", "autenticação inválida")
            State.authenticationState.postValue( AuthenticationState.INVALID_AUTHENTICATION )
            AuthenticationState.INVALID_AUTHENTICATION
        }
    }

    /** TODO [API]: Link to Backend **/
    private fun passwordIsValidForEmail(email: String, password: String): Boolean {
        return true
    }

    /** TODO [API]: Link to Backend **/
    private fun fetchUser(email: String): User {
        var user: User = User(
            name ="Sample Name",
            email = email,
            birth = Date(),
            age = 21,
            gender = 'M',
            token = "s4mpl3t0kEn"
        )
        user.cases = fetchCases(email)
        State.user.postValue(user)
        return user
    }

    private fun fetchCases(email: String): MutableList<Case> {
        return mutableListOf<Case>()
    }

}