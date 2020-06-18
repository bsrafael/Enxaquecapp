package com.enxaquecapp.app.shared

import androidx.lifecycle.MutableLiveData
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.model.Episode
import com.enxaquecapp.app.model.User

class State {
    companion object {
        val authenticationState: MutableLiveData<AuthenticationState> = MutableLiveData<AuthenticationState>()
        var user: MutableLiveData<User> = MutableLiveData<User>()
        var token: MutableLiveData<String> = MutableLiveData<String>()
        var episodes: MutableLiveData<List<Episode>> = MutableLiveData<List<Episode>>()
    }
}