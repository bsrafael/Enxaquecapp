package com.enxaquecapp.app.api.client

import android.util.Log
import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.ClientFactory
import com.enxaquecapp.app.model.Cause
import com.enxaquecapp.app.model.Location
import com.enxaquecapp.app.model.Relief
import com.enxaquecapp.app.model.User
import com.enxaquecapp.app.shared.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OptionClient {

    fun getCauses(callback: ApiCallback<List<Cause>>) {
        val call = ClientFactory(State.token.value).causeService().getCauses()
        call.enqueue(object : Callback<List<Cause>?> {

            override fun onResponse(call: Call<List<Cause>?>?, response: Response<List<Cause>?>?) {
                response?.body()?.let {
                    val vm: List<Cause> = it
                    callback.success(vm)
                }

                response?.errorBody()?.let {
                    val errorCode = response.code()
                    val message = it.string()
                    callback.failure(errorCode, message)
                }
            }

            override fun onFailure(call: Call<List<Cause>?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }

    fun getReliefs(callback: ApiCallback<List<Relief>>) {
        val call = ClientFactory(State.token.value).reliefService().getReliefs()
        call.enqueue(object : Callback<List<Relief>?> {

            override fun onResponse(call: Call<List<Relief>?>?, response: Response<List<Relief>?>?) {
                response?.body()?.let {
                    val vm: List<Relief> = it
                    callback.success(vm)
                }

                response?.errorBody()?.let {
                    val errorCode = response.code()
                    val message = it.string()
                    callback.failure(errorCode, message)
                }
            }

            override fun onFailure(call: Call<List<Relief>?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }

    fun getLocations(callback: ApiCallback<List<Location>>) {
        val call = ClientFactory(State.token.value).locationService().getLocations()
        call.enqueue(object : Callback<List<Location>?> {

            override fun onResponse(call: Call<List<Location>?>?, response: Response<List<Location>?>?) {
                response?.body()?.let {
                    val vm: List<Location> = it
                    callback.success(vm)
                }

                response?.errorBody()?.let {
                    val errorCode = response.code()
                    val message = it.string()
                    callback.failure(errorCode, message)
                }
            }

            override fun onFailure(call: Call<List<Location>?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }
}