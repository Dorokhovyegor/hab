package com.dorokhov.hab.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.dorokhov.cycleprogress.CycleProgressView
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
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

fun Context.getCurrentDate(): String {
    val pattern = "dd-MM-yyyy"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
    val c = Calendar.getInstance()
    return simpleDateFormat.format(c.time).toString()
}

inline fun TextInputEditText.onTextChanged(crossinline block: (count: Int) -> Unit): TextWatcher {
    val textWatcher = object: TextWatcher{
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            block.invoke(count)
        }
    }
    addTextChangedListener(textWatcher)
    return textWatcher
}

fun View.visible(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.isVisible(): Boolean{
    return visibility == View.VISIBLE
}
