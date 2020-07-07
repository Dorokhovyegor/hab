package com.dorokhov.hab.utils

import android.content.Context
import android.widget.Toast
import com.dorokhov.cycleprogress.CycleProgressView
import java.util.*

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.getRandomCell(): CycleProgressView {
    return CycleProgressView(
        this, null, 0
    ).apply {
        typeChecked = Random().nextInt(4)
    }
}