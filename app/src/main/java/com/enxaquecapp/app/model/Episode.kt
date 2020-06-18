package com.enxaquecapp.app.model

import com.enxaquecapp.app.R
import java.util.*

data class Episode (
    val id: UUID,
    val start: Date,
    val end: Date? = null,
    val intensity: Int,
    val location: Location? = null,
    val cause: Cause? = null,
    val relief: Relief? = null,
    val releafWorked: Boolean = null
) {

    fun getIcon(): Int {
        return selectIcon(intensity)
    }

    companion object {
        fun selectIcon(intensity: Int?): Int {
            return if ( intensity == null ) {
                R.drawable.ic_no_icon
            } else {
                val i = intensity!!

                when {
                    i > 7 -> {
                        R.drawable.ic_pain
                    }
                    i in 4..7 -> {
                        R.drawable.ic_meh
                    }
                    else -> {
                        R.drawable.ic_smile
                    }
                }
            }
        }
    }

}

