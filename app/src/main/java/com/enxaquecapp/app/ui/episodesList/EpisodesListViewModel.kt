package com.enxaquecapp.app.ui.episodesList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.client.EpisodeClient
import com.enxaquecapp.app.model.Cause
import com.enxaquecapp.app.model.Episode
import com.enxaquecapp.app.model.Location
import com.enxaquecapp.app.model.Relief
import com.enxaquecapp.app.shared.State
import java.util.*

class EpisodesListViewModel: ViewModel() {

    var episodes: MutableLiveData<List<Episode>> = MutableLiveData<List<Episode>>()
    var error: MutableLiveData<String> = MutableLiveData<String>()

    fun update() {
        val client = EpisodeClient()

        client.get(object: ApiCallback<List<Episode>> {
            override fun success(response: List<Episode>) {
                episodes.postValue(response)
                State.episodes.postValue(response)
            }

            override fun noContent() {
                error.postValue("Nenhum episódio cadastrado")
                episodes.postValue(emptyList())
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("HomeViewModel", "falha ao carregar os episódios: ($errorCode) $message")
                error.postValue("Falha ao carregar os episódios\n$message")
            }

            override fun error() {
                Log.e("UserRepository", "falha interna ao carregar os episódios")
                error.postValue("Falha interna ao carregar os episódios")
            }
        })
    }
}