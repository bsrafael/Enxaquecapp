package com.enxaquecapp.app.ui.medicines

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.enxaquecapp.app.model.Medicine
import java.util.*

import com.enxaquecapp.app.api.ApiCallback
import com.enxaquecapp.app.api.client.MedicationClient
import com.enxaquecapp.app.api.models.input.MedicationInputModel
import com.enxaquecapp.app.model.Interval
import java.time.LocalTime

class MedicinesViewModel: ViewModel() {

    private var meds: MutableList<Medicine> = mutableListOf()

    var medicines: MutableLiveData<MutableList<Medicine>> =
        MutableLiveData<MutableList<Medicine>>(meds)

    var intervals: MutableList<Interval> = mutableListOf()


    init {
        update()
    }

    fun update() {
        mockIntervals()
        loadMedicines()
    }

    fun getIntervalArray(): Array<String> {
        val list: MutableList<String> = mutableListOf()

        for (interval in intervals) {
            list.add(interval.displayValue)
        }

        return list.toTypedArray()
    }

    private fun mockIntervals() {
        intervals.clear()
        intervals.add(Interval("2/2h",  "02:00:00"))
        intervals.add(Interval("3/3h", "03:00:00"))
        intervals.add(Interval("4/4h", "04:00:00"))
        intervals.add(Interval("6/6h", "06:00:00"))
        intervals.add(Interval("8/8h", "08:00:00"))
        intervals.add(Interval("12/12h", "12:00:00"))
        intervals.add(Interval("1x/dia", "24:00:00"))
        intervals.add(Interval("Necessidade", "00:00:00"))
    }

    fun getInterval(selected: String): String {
        val interval = intervals.filter { i -> i.displayValue.compareTo(selected) == 0 }
        return interval[0].usefulValue
    }

    fun getIntervalDisplayValue(selected: String): String {
        val interval = intervals.filter { i -> i.usefulValue.compareTo(selected) == 0 }
        return interval[0].displayValue
    }

    fun loadMedicines() {
        val client = MedicationClient()

        client.get(object: ApiCallback<List<Medicine>> {

            override fun success(response: List<Medicine>) {
                meds.clear()
                meds.addAll(response)
                medicines.postValue(meds)
                Log.i("MedicinesViewModel", "medicamentos carregados com sucesso")
            }

            override fun noContent() {
                Log.i("MedicinesViewModel", "no content")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("MedicinesViewModel", "falha ao carregar os medicamentos ($errorCode) $message")
                // TODO(Rafael) error handling
            }

            override fun error() {
                Log.e("MedicinesViewModel", "falha interna ao carregar os medicamentos")
                // TODO(Rafael) error handling
            }
        })
    }

    fun add(im: MedicationInputModel) {
        val client = MedicationClient()

        client.create(im, object: ApiCallback<Medicine> {

            override fun success(response: Medicine) {
                meds.add(0, response)
                medicines.postValue(meds)
                Log.i("MedicinesViewModel", "medicamento criado com sucesso")
            }

            override fun noContent() {
                Log.i("MedicinesViewModel", "no content")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("MedicinesViewModel", "falha ao criar o medicamento ($errorCode) $message")
                // TODO(Rafael) error handling
            }

            override fun error() {
                Log.e("MedicinesViewModel", "falha interna na criação do medicamento")
                // TODO(Rafael) error handling
            }
        })
    }

    // TODO(Rafael) usar esse método no submit do medicamento quando estiver alterando um medicamento existente
    fun update(id: UUID, im: MedicationInputModel) {
        val client = MedicationClient()

        client.update(id, im, object: ApiCallback<Medicine> {

            override fun success(response: Medicine) {
                meds.filter { med -> med.id != id }
                meds.add(0, response)
                medicines.postValue(meds)
                Log.i("MedicinesViewModel", "medicamento atualizado com sucesso")
            }

            override fun noContent() {
                Log.i("MedicinesViewModel", "no content")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("MedicinesViewModel", "falha ao atualizar o medicamento ($errorCode) $message")
                // TODO(Rafael) error handling
            }

            override fun error() {
                Log.e("MedicinesViewModel", "falha interna na atualização do medicamento")
                // TODO(Rafael) error handling
            }
        })
    }

    fun remove(id: UUID) {
        val client = MedicationClient()

        client.delete(id, object: ApiCallback<Void> {

            override fun success(response: Void) {
                meds.filter { med -> med.id != id }
                medicines.postValue(meds)
                Log.i("MedicinesViewModel", "medicamento removido com sucesso")
            }

            override fun noContent() {
                Log.i("MedicinesViewModel", "no content")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("MedicinesViewModel", "falha ao remover o medicamento ($errorCode) $message")
                // TODO(Rafael) error handling
            }

            override fun error() {
                Log.e("MedicinesViewModel", "falha interna na remoção do medicamento")
                // TODO(Rafael) error handling
            }
        })
    }

    fun finish(id: UUID) {
        val client = MedicationClient()

        client.finish(id, object: ApiCallback<Medicine> {

            override fun success(response: Medicine) {
                meds.filter { med -> med.id != id }
                meds.add(0, response)
                medicines.postValue(meds)
                Log.i("MedicinesViewModel", "medicamento atualizado com sucesso")
            }

            override fun noContent() {
                Log.i("MedicinesViewModel", "no content")
            }

            override fun failure(errorCode: Int, message: String) {
                Log.i("MedicinesViewModel", "falha ao atualizar o medicamento ($errorCode) $message")
                // TODO(Rafael) error handling
            }

            override fun error() {
                Log.e("MedicinesViewModel", "falha interna na atualização do medicamento")
                // TODO(Rafael) error handling
            }
        })
    }
}