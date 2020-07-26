package com.dorokhov.hab.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toStringDate(): String {
    val pattern = "dd-MM-yyyy"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
    return simpleDateFormat.format(this).toString()
}
