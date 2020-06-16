package com.enxaquecapp.app.shared

import java.text.SimpleDateFormat
import java.util.*

object StringUtils {
    fun strToDate(dateString: String): Date {
        return SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).parse(dateString)!!

    }
    fun dateToString(dateLong: Long): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(Date(dateLong))
    }

    fun dateToString(date: Date): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(date)
    }
}