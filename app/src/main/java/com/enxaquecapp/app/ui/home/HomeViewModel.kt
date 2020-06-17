package com.enxaquecapp.app.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.client.EpisodeClient
import com.enxaquecapp.app.api.models.view.TokenViewModel
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.model.Episode
import com.enxaquecapp.app.model.User
import com.enxaquecapp.app.shared.State

class HomeViewModel : ViewModel() {

    var greetingText: MutableLiveData<String> = MutableLiveData<String>()
    var casesText: MutableLiveData<String> = MutableLiveData<String>()
    var episodes: MutableLiveData<List<Episode>> = MutableLiveData<List<Episode>>()
    lateinit var user: User

    init {

        State.user.observeForever {
            user = it
            update()
        }
    }

    fun update() {
        if (this::user.isInitialized) {
            greetingText.postValue(buildGreetingText())

            val client = EpisodeClient()

            client.get(object: ApiCallback<List<Episode>> {
                override fun success(response: List<Episode>) {
                    episodes.postValue(response)
                    casesText.postValue(buildCasesText(response.size))
                    Log.i("HomeViewModel", "lista de episódios atualizada")
                }

                override fun failure(errorCode: Int, message: String) {
                    Log.i("HomeViewModel", "falha ao carregar os episódios: ($errorCode) $message")
                    // TODO(rafael) fun error handling and/or message
                }

                override fun error() {
                    Log.e("UserRepository", "falha ao carregar os episódios")
                    // TODO(rafael) fun error handling and/or message
                }
            })
        }
    }

    private fun buildGreetingText() : String {
        return "Olá, ${user.name.split(' ')[0]}"
    }

    private fun buildCasesText(cases: Int) : String {
        return "Você teve $cases nos últimos dias."
    }
}