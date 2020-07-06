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