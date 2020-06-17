package com.enxaquecapp.app.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.client.AuthenticationClient
import com.enxaquecapp.app.api.models.input.TokenInputModel
import com.enxaquecapp.app.api.models.view.TokenViewModel
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.model.User
import com.enxaquecapp.app.shared.State
import java.util.*

class LoginViewModel : ViewModel() {

    /** This method serves the sole purpose of hitting Heroku API for it to be turned on. **/
    fun turnOnApi() {
        val client = AuthenticationClient()
        client.getToken(TokenInputModel("boot@enxaquecapp.com", "sudo"), object: ApiCallback<TokenViewModel> {
            override fun success(response: TokenViewModel) {
                Log.i("LoginVM", "Heroku is alive!")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("LoginVM", "Heroku is waking up: ($errorCode) $message")
            }

            override fun error() {
                Log.e("LoginVM", "Internal error")
            }
        })
    }

    fun authenticate(email: String, password: String) {
        Log.i("LoginViewModel", "autenticando")

        val client = AuthenticationClient()
        client.getToken(TokenInputModel(email, password), object: ApiCallback<TokenViewModel> {
            override fun success(response: TokenViewModel) {
                State.user.postValue(response.user)
                State.token.postValue(response.token)
                State.authenticationState.postValue(AuthenticationState.AUTHENTICATED)
                Log.i("UserRepository", "user autenticado")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("UserRepository", "autenticação inválida: ($errorCode) $message")
                State.authenticationState.postValue(AuthenticationState.INVALID_AUTHENTICATION)
            }

            override fun error() {
                Log.e("UserRepository", "falha na autenticação")
                State.authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
            }
        })

    }

    fun refuseAuthentication() {
        State.authenticationState.postValue(AuthenticationState.INVALID_AUTHENTICATION)
    }
}
