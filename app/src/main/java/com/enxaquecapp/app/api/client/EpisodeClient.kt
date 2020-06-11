package com.enxaquecapp.app.api.client

import android.util.Log
import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.ClientFactory
import com.enxaquecapp.app.api.models.input.EpisodeInputModel
import com.enxaquecapp.app.api.models.input.EpisodePatchInputModel
import com.enxaquecapp.app.model.Episode
import com.enxaquecapp.app.model.User
import com.enxaquecapp.app.shared.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import java.util.*

class EpisodeClient {

    fun get(callback: ApiCallback<List<Episode>>) {
        val call = ClientFactory(State.token.value).episodeService().getEpisodes()
        call.enqueue(object : Callback<List<Episode>?> {

            override fun onResponse(call: Call<List<Episode>?>?, response: Response<List<Episode>?>?) {
                response?.body()?.let {
                    val vm: List<Episode> = it
                    callback.success(vm)
                }

                response?.errorBody()?.let {
                    val errorCode = response.code()
                    val message = it.string()
                    callback.failure(errorCode, message)
                }
            }

            override fun onFailure(call: Call<List<Episode>?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }

    fun create(im: EpisodeInputModel, callback: ApiCallback<Episode>) {
        val call = ClientFactory(State.token.value).episodeService().create(im)
        call.enqueue(object : Callback<Episode?> {

            override fun onResponse(call: Call<Episode?>?, response: Response<Episode?>?) {
                response?.body()?.let {
                    val vm: Episode = it
                    callback.success(vm)
                }

                response?.errorBody()?.let {
                    val errorCode = response.code()
                    val message = it.string()
                    callback.failure(errorCode, message)
                }
            }

            override fun onFailure(call: Call<Episode?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }

    fun update(id: UUID, im: EpisodePatchInputModel, callback: ApiCallback<Episode>) {
        val call = ClientFactory(State.token.value).episodeService().update(id, im)
        call.enqueue(object : Callback<Episode?> {

            override fun onResponse(call: Call<Episode?>?, response: Response<Episode?>?) {
                response?.body()?.let {
                    val vm: Episode = it
                    callback.success(vm)
                }

                response?.errorBody()?.let {
                    val errorCode = response.code()
                    val message = it.string()
                    callback.failure(errorCode, message)
                }
            }

            override fun onFailure(call: Call<Episode?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }

    fun delete(id: UUID, callback: ApiCallback<Void>) {
        val call = ClientFactory(State.token.value).episodeService().delete(id)
        call.enqueue(object : Callback<Void?> {

            override fun onResponse(call: Call<Void?>?, response: Response<Void?>?) {
                response?.body()?.let {
                    val vm: Void = it // TODO(Julio) no idea if this is gonna work
                    callback.success(vm)
                }

                response?.errorBody()?.let {
                    val errorCode = response.code()
                    val message = it.string()
                    callback.failure(errorCode, message)
                }
            }

            override fun onFailure(call: Call<Void?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }
}