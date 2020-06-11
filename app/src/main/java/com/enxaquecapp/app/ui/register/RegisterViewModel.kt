package com.enxaquecapp.app.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.client.UserClient
import com.enxaquecapp.app.api.models.input.TokenInputModel
import com.enxaquecapp.app.api.models.input.UserInputModel
import com.enxaquecapp.app.api.models.input.UserPatchInputModel
import com.enxaquecapp.app.api.models.view.TokenViewModel
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.model.User
import com.enxaquecapp.app.shared.State
import java.util.*

class RegisterViewModel: ViewModel() {

    var user: User? = null
    get() {
        if (field == null)
            field = State.user.value
        return field
    }

    fun register(user: UserInputModel) {
        Log.i("RegisterViewModel", "registrando")

        val client = UserClient()

        client.register(user, object: ApiCallback<TokenViewModel> {

            override fun success(response: TokenViewModel) {
                State.user.postValue(response.user)
                State.token.postValue(response.token)
                State.authenticationState.postValue(AuthenticationState.AUTHENTICATED)
                Log.i("UserRepository", "usuário criado com sucesso")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("UserRepository", "falha ao criar o usuário ($errorCode) $message")
                State.authenticationState.postValue(AuthenticationState.INVALID_AUTHENTICATION)
            }

            override fun error() {
                Log.e("UserRepository", "falha interna na criação do usuário")
                State.authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
            }
        })
    }

    fun update(user: UserPatchInputModel) {
        Log.i("RegisterViewModel", "atualizando")

        val client = UserClient()

        client.update(user, object: ApiCallback<User> {

            override fun success(response: User) {
                State.user.postValue(response)
                Log.i("UserRepository", "usuário criado com sucesso")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("UserRepository", "falha ao criar o usuário ($errorCode) $message")
                // TODO(rafael) show the message?
            }

            override fun error() {
                Log.e("UserRepository", "falha interna na criação do usuário")
                // TODO(rafael) show the message?
            }
        })
    }
}