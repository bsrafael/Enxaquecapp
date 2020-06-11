package com.enxaquecapp.app.ui.medicines

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.model.Medicine

class MedicinesViewModel: ViewModel() {

    private var meds: MutableList<Medicine> = mutableListOf()

    var lastAddedId: Int = 0

    var medicines: MutableLiveData<MutableList<Medicine>> =
        MutableLiveData<MutableList<Medicine>>(meds)


    init {
    }

    /**
     * TODO:
     * - API Call
     * - Better ID handling
     * **/
    fun add(medicine: Medicine) {
        medicine.id = lastAddedId
        lastAddedId += 1

        meds.add(0, medicine)

        Log.i("MedicinesVM", meds.toString())
        // TODO: API Call

        medicines.postValue(meds)
    }

    fun remove(medId: Int) {
        meds.filter { med -> med.id != medId }
        // TODO: API Call
        medicines.postValue(meds)
    }

    // TODO: API Call
    fun update() {

    }




}