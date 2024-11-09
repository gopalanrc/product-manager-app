package com.thales.productmanager.util

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT

fun EditText.trimmedText(): String {
    return text.toString().trim()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, LENGTH_LONG).show()
}

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, LENGTH_SHORT).show()
}