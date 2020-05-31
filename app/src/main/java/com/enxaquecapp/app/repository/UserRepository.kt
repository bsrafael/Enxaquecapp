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
    fun register(user: User) {
        Log.i("UserRepository", "registrando")
        user.token = getToken()
        State.user.postValue(user)
        State.authenticationState.postValue(AuthenticationState.AUTHENTICATED)
    }

    /** TODO [API]: Link to Backend **/
    fun update(user: User) {
        Log.i("UserRepository", "atualizando")
        State.user.postValue(user)

    }

    /** TODO [API]: Link to Backend **/
    fun getToken(): String {
        return "ReplaceMeWithActualTokenEventually"
    }


    /** TODO [API]: Link to Backend **/
    private fun passwordIsValidForEmail(email: String, password: String): Boolean {
        return true
    }

    /** TODO [API]: Replace with actual user from Backend **/
    private fun fetchUser(email: String): User {
        var user: User = User(
            name ="Sample Name",
            email = email,
            birth = Date(),
            age = 21,
            gender = 'M'
        )
        user.cases = fetchCases(email)
        State.user.postValue(user)
        return user
    }

    /** TODO [API]: Link to Backend **/
    private fun fetchCases(email: String): MutableList<Case> {
        return mutableListOf<Case>()
    }


}