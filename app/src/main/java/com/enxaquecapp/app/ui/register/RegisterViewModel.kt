package com.enxaquecapp.app.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.model.User
import com.enxaquecapp.app.repository.UserRepository

class RegisterViewModel: ViewModel() {

    var userRepository: UserRepository = UserRepository()

    fun register(user: User) {
        Log.i("RegisterViewModel", "registrando")
        userRepository.register(user)
    }



}