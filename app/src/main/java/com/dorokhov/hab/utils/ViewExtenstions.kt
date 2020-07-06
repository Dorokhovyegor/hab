package com.dorokhov.hab.utils

import android.content.Context
import android.widget.Toast
import com.dorokhov.hab.ui.ResponseType

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}