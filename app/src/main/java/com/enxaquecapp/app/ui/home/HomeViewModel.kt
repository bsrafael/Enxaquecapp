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

    var greetingText: MutableLiveData<String> = MutableLiveData<String>()
    var casesText: MutableLiveData<String> = MutableLiveData<String>()
    lateinit var user: User

    var welcomeShown: Boolean = false

    var episodesViewModel: EpisodesListViewModel = EpisodesListViewModel()

    var error: MutableLiveData<String> = MutableLiveData<String>()

    init {

        State.user.observeForever {
            user = it
            update()
        }

        State.episodes.observeForever {
            casesText.postValue(buildCasesText(it.size))
        }

        episodesViewModel.error.observeForever {
            error.postValue(it)
        }
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
}