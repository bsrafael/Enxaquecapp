package com.enxaquecapp.app.ui.episode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.client.EpisodeClient
import com.enxaquecapp.app.api.models.input.EpisodeInputModel
import com.enxaquecapp.app.api.models.input.EpisodePatchInputModel
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.model.Episode
import com.enxaquecapp.app.shared.State
import java.util.*

class EpisodeViewModel: ViewModel() {

    var episode: MutableLiveData<Episode> = MutableLiveData<Episode>()

    fun create(im: EpisodeInputModel) {
        Log.i("RegisterViewModel", "criando episódio")

        val client = EpisodeClient()

        client.create(im, object: ApiCallback<Episode> {

            override fun success(response: Episode) {
                episode.postValue(response)
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

    fun update(id: UUID, im: EpisodePatchInputModel) {
        Log.i("RegisterViewModel", "atualizando episódio")

        val client = EpisodeClient()

        client.update(id, im, object: ApiCallback<Episode> {

            override fun success(response: Episode) {
                episode.postValue(response)
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
}