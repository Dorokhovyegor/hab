package com.dorokhov.hab.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.getEndOfCycle(): String {
    val pattern = "dd-MM-yyyy"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
    val date = simpleDateFormat.parse(this)
    val dateInMillisecons = date?.time
    val endCycleInMilliseconds = dateInMillisecons?.plus(1209600000)
    return endCycleInMilliseconds?.let { endCycle ->
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = endCycle
        simpleDateFormat.format(endCycle).toString()
    }?: "0"
}

fun String.isCurrentDayOfWeek(dayWeek: Int): Boolean {
    val pattern = "dd-MM-yyyy"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
    val date = simpleDateFormat.parse(this)
    val c = Calendar.getInstance()
    c.time = date
    val dayOfWeek = c[Calendar.DAY_OF_WEEK]
    println(dayOfWeek)
    val dayWeekLocal = when(dayWeek) {
        7 -> 1
        6 -> 7
        5 -> 6
        4 -> 5
        3 -> 4
        2 -> 3
        1 -> 2
        else -> -1
    }

    if (dayWeekLocal == -1) {
        return false
    }

    if (dayOfWeek == dayWeekLocal) {
        return true
    }
    return false
}