package com.enxaquecapp.app.api.client

import android.util.Log
import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.ClientFactory
import com.enxaquecapp.app.api.models.input.UserInputModel
import com.enxaquecapp.app.api.models.view.TokenViewModel
import com.enxaquecapp.app.model.User
import com.enxaquecapp.app.shared.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserClient {

    fun getCurrent(callback: ApiCallback<User>) {
        val call = ClientFactory(State.token.value).userService().getCurrent()
        call.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>?, response: Response<User?>?) {
                response?.body()?.let {
                val vm: User = it
                callback.success(vm)
                }
            }

            override fun onFailure(call: Call<User?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }

    fun register(im: UserInputModel, callback: ApiCallback<TokenViewModel>) {
        val call = ClientFactory(State.token.value).userService().register(im)
        call.enqueue(object : Callback<TokenViewModel?> {
            override fun onResponse(call: Call<TokenViewModel?>?, response: Response<TokenViewModel?>?) {
                response?.body()?.let {
                    val vm: TokenViewModel = it
                    callback.success(vm)
                }

                response?.errorBody()?.let {
                    Log.e("APi error:", it.string())
                }
            }

            override fun onFailure(call: Call<TokenViewModel?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }
}