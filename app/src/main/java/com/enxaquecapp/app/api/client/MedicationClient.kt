package com.enxaquecapp.app.api.client

import android.util.Log
import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.ClientFactory
import com.enxaquecapp.app.api.models.input.MedicationInputModel
import com.enxaquecapp.app.model.Medicine
import com.enxaquecapp.app.shared.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MedicationClient {

    fun get(callback: ApiCallback<List<Medicine>>) {
        val call = ClientFactory(State.token.value).medicationService().get()
        call.enqueue(object : Callback<List<Medicine>?> {

            override fun onResponse(call: Call<List<Medicine>?>?, response: Response<List<Medicine>?>?) {
                response?.body()?.let {
                    val vm: List<Medicine> = it
                    callback.success(vm)
                }

                response?.errorBody()?.let {
                    val errorCode = response.code()
                    val message = it.string()
                    callback.failure(errorCode, message)
                }
            }

            override fun onFailure(call: Call<List<Medicine>?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }

    fun create(im: MedicationInputModel, callback: ApiCallback<Medicine>) {
        val call = ClientFactory(State.token.value).medicationService().create(im)
        call.enqueue(object : Callback<Medicine?> {

            override fun onResponse(call: Call<Medicine?>?, response: Response<Medicine?>?) {
                response?.body()?.let {
                    val vm: Medicine = it
                    callback.success(vm)
                }

                response?.errorBody()?.let {
                    val errorCode = response.code()
                    val message = it.string()
                    callback.failure(errorCode, message)
                }
            }

            override fun onFailure(call: Call<Medicine?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }

    fun update(id: UUID, im: MedicationInputModel, callback: ApiCallback<Medicine>) {
        val call = ClientFactory(State.token.value).medicationService().update(id, im)
        call.enqueue(object : Callback<Medicine?> {

            override fun onResponse(call: Call<Medicine?>?, response: Response<Medicine?>?) {
                response?.body()?.let {
                    val vm: Medicine = it
                    callback.success(vm)
                }

                response?.errorBody()?.let {
                    val errorCode = response.code()
                    val message = it.string()
                    callback.failure(errorCode, message)
                }
            }

            override fun onFailure(call: Call<Medicine?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }

    fun delete(id: UUID) {
        val call = ClientFactory(State.token.value).medicationService().delete(id)
        call.enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

    fun finish(id: UUID, callback: ApiCallback<Medicine>) {
        val call = ClientFactory(State.token.value).medicationService().finish(id)
        call.enqueue(object : Callback<Medicine?> {

            override fun onResponse(call: Call<Medicine?>?, response: Response<Medicine?>?) {
                response?.body()?.let {
                    val vm: Medicine = it
                    callback.success(vm)
                }

                response?.errorBody()?.let {
                    val errorCode = response.code()
                    val message = it.string()
                    callback.failure(errorCode, message)
                }
            }

            override fun onFailure(call: Call<Medicine?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                callback.error()
            }
        })
    }
}