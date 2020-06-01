package com.enxaquecapp.app.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.client.UserClient
import com.enxaquecapp.app.api.models.input.TokenInputModel
import com.enxaquecapp.app.api.models.input.UserInputModel
import com.enxaquecapp.app.api.models.view.TokenViewModel
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.model.User
import com.enxaquecapp.app.shared.State
import java.util.*

class RegisterViewModel: ViewModel() {

    fun register(user: User, password: String) {
        Log.i("RegisterViewModel", "registrando")

        val client = UserClient()
        val im = UserInputModel(
            name = user.name,
            email = user.email,
            password = password,
            birthDate = user.birthDate,
            gender = user.gender
        )

        client.register(im, object: ApiCallback<TokenViewModel> {

            override fun success(response: TokenViewModel) {
                State.user.postValue(response.user)
                State.token.postValue(response.token)
                State.authenticationState.postValue(AuthenticationState.AUTHENTICATED)
                Log.i("UserRepository", "user autenticado")
            }

            override fun error() {
                Log.i("UserRepository", "autenticação inválida")
                State.authenticationState.postValue(AuthenticationState.INVALID_AUTHENTICATION)
            }
        })
    }
}