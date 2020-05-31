package com.enxaquecapp.app.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.model.User
import com.enxaquecapp.app.repository.UserRepository
import com.enxaquecapp.app.shared.State

class HomeViewModel : ViewModel() {

    var greetingText: MutableLiveData<String> = MutableLiveData<String>()
    var casesText: MutableLiveData<String> = MutableLiveData<String>()
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
            casesText.postValue( buildCasesText() )
            Log.i("HomeViewModel", "update() \ngreetingText: ${greetingText.value} \tcasesText: ${casesText.value}")
        }
    }


    private fun buildGreetingText() : String {
        return "Olá, ${user.name.split(' ')[0]}"
    }

    private fun buildCasesText() : String {
        return "Você teve ${user.cases.size} nos últimos dias."
    }
}