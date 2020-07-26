package com.dorokhov.hab.utils

import android.util.Log

object Logger {

    fun loge(obj: Any?) {
        obj?.let {
            Log.e("Hab application", "$obj")
        }?: Log.e("Hab application", "null")
    }

}