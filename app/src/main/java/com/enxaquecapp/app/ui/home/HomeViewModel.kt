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
import com.enxaquecapp.app.ui.episodesList.EpisodesListViewModel

class HomeViewModel : ViewModel() {

    var greetingText: MutableLiveData<String> = MutableLiveData<String>("Boas vindas!")
    var casesText: MutableLiveData<String> = MutableLiveData<String>()
    var meanIntensity: MutableLiveData<Float> = MutableLiveData<Float>()

    lateinit var user: User

    var welcomeShown: Boolean = false

    private var episodesViewModel: EpisodesListViewModel = EpisodesListViewModel()

    var error: MutableLiveData<String> = MutableLiveData<String>()

    init {


        setupObservers()


    }

    fun update() {
        if (this::user.isInitialized) {
            greetingText.postValue(buildGreetingText())
        }

        episodesViewModel.update()
    }

    private fun buildGreetingText() : String {
        return "Olá, ${user.name.split(' ')[0]}"
    }

    private fun buildCasesText(cases: Int) : String {
        return "Você teve $cases nos últimos dias."
    }

    private fun setupObservers() {
        State.user.observeForever {
            user = it
            update()
        }

        State.episodes.observeForever {
            var txt = ""
            txt = if (it.isNotEmpty()) {
                var mean = getMeanIntensity(it)
                meanIntensity.postValue(mean)
                "Você tem ${it.size} episódios registrados.\nSeus episódios têm uma intensidade média de $mean"
            } else {
                "Você ainda não adicionou nenhum episódio!"
            }
            casesText.postValue(txt)
        }

        episodesViewModel.error.observeForever {
            error.postValue(it)
        }
    }

    private fun getMeanIntensity(eps: List<Episode>): Float {
        var counter: Int = 0

        eps.forEach {ep ->
            counter += ep.intensity
        }

        return counter.toFloat() / eps.size.toFloat()
    }

}