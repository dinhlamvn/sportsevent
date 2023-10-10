package com.inspius.sportsevent.extensions

import android.app.Activity
import android.widget.Toast

fun Activity.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}