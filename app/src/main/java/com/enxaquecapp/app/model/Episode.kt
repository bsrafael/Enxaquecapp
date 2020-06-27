package com.enxaquecapp.app.model

import com.enxaquecapp.app.R
import java.io.Serializable
import java.util.*

data class Episode (
    val id: UUID,
    val start: Date,
    val end: Date? = null,
    val intensity: Int,
    val location: Location? = null,
    val cause: Cause? = null,
    val relief: Relief? = null,
    val releafWorked: Boolean // TODO(Julio) fix typo on backend
): Serializable {

    fun getIcon(): Int {
        return selectIcon(intensity)
    }

    fun getDuration(): String {
        var duration = "?"
        if (end != null) {
            val diff = end!!.time - start.time
            val diffSeconds = diff / 1000 % 60
            val diffMinutes = diff / (60 * 1000) % 60
            val diffHours = diff / (60 * 60 * 1000)
            duration = ""
            if (diffHours > 0) duration += "${diffHours}h "
            if (diffMinutes > 0) duration += "${diffMinutes}min "
            if (diffSeconds > 0) duration += "${diffSeconds}s "
        }

        return duration
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

