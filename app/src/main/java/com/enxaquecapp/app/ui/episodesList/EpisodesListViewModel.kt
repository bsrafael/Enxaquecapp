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

        error.postValue("TODO: Remover mock de episódios")
        mockEpisodes()

        // TODO(Julio): D/OkHttp: <-- 204 No Content https://exaquecapp.herokuapp.com/api/episodes não chama nenhum callback
//        client.get(object: ApiCallback<List<Episode>> {
//            override fun success(response: List<Episode>) {
//                episodes.postValue(response)
//                State.episodes.postValue(response)
//            }
//
//            override fun failure(errorCode: Int, message: String) {
//                Log.i("HomeViewModel", "falha ao carregar os episódios: ($errorCode) $message")
//                error.postValue("Falha ao carregar os episódios\n$message")
//                mockEpisodes()
//            }
//
//            override fun error() {
//                Log.e("UserRepository", "falha interna ao carregar os episódios")
//                error.postValue("Falha interna ao carregar os episódios")
//                mockEpisodes()
//            }
//        })
    }

    private fun mockEpisodes() {
        val eps = listOf(
            Episode(
                UUID.randomUUID(),
                Date(),
                "12:00:00",
                Date(),
                "13:00:00",
                9,
                Location(UUID.randomUUID(), "random place"),
                Cause(UUID.randomUUID(), "random cause 1"),
                Relief(UUID.randomUUID(), "random relief 1"),
                false
            ),
            Episode(
                UUID.randomUUID(),
                Date(),
                "17:00:00",
                Date(),
                "19:00:00",
                5,
                Location(UUID.randomUUID(), "random place 2"),
                Cause(UUID.randomUUID(), "random cause 2"),
                Relief(UUID.randomUUID(), "random relief 2"),
                false
            ),
            Episode(
                UUID.randomUUID(),
                Date(),
                "21:00:00",
                Date(),
                "23:00:00",
                2,
                Location(UUID.randomUUID(), "random place 3"),
                Cause(UUID.randomUUID(), "random cause 3"),
                Relief(UUID.randomUUID(), "random relief 3"),
                false
            )
        )

        episodes.postValue(eps)
        State.episodes.postValue(eps)

    }
}