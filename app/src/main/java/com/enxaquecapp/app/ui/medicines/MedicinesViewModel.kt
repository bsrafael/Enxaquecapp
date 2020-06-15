package com.enxaquecapp.app.ui.medicines

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enxaquecapp.app.model.Interval
import com.enxaquecapp.app.model.Medicine
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.schedule

class MedicinesViewModel: ViewModel() {

    private var meds: MutableList<Medicine> = mutableListOf()

    var lastAddedId: Int = 0

    var medicines: MutableLiveData<MutableList<Medicine>> =
        MutableLiveData<MutableList<Medicine>>(meds)

    var intervals: MutableList<Interval> = mutableListOf()


    init {
        update()
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
        Timer("fake API call", false).schedule(500) {
            medicines.postValue(meds)
        }

    }

    fun remove(medId: Int) {
        val m = meds.filter { med -> med.id != medId }
        meds = m as MutableList<Medicine>

        // TODO: API Call
        Timer("fake API call", false).schedule(500) {
            medicines.postValue(meds)
        }
    }

    // TODO: API Call
    fun update() {
        // TODO: get medicines

        mockIntervals()
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
        intervals.add( Interval("2/2h", "2:00:00") )
        intervals.add( Interval("3/3h", "3:00:00") )
        intervals.add( Interval("4/4h", "4:00:00") )
        intervals.add( Interval("6/6h", "6:00:00") )
        intervals.add( Interval("8/8h", "8:00:00") )
        intervals.add( Interval("12/12h", "12:00:00") )
        intervals.add( Interval("1x/dia", "24:00:00") )
        intervals.add( Interval("Necessidade", "00:00:00") )
    }

    fun getInterval(selected: String): Interval {
        val interval = intervals.filter { i -> i.displayValue.compareTo(selected) == 0 }
        return interval[0]
    }




}