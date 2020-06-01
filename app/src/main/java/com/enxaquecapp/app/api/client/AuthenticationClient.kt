package com.enxaquecapp.app.api.client

import android.util.Log
import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.ClientFactory
import com.enxaquecapp.app.api.models.input.TokenInputModel
import com.enxaquecapp.app.api.models.view.TokenViewModel
import com.enxaquecapp.app.shared.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationClient {

    fun getToken(im: TokenInputModel, callback: ApiCallback<TokenViewModel>) {
        val call = ClientFactory(State.token.value).authenticationService().authenticate(im)
        call.enqueue(object : Callback<TokenViewModel?> {
            override fun onResponse(call: Call<TokenViewModel?>?, response: Response<TokenViewModel?>?) {
                response?.body()?.let {
                    val vm: TokenViewModel = it
                    callback.success(vm)
                }
            }

            override fun onFailure(call: Call<TokenViewModel?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }
}