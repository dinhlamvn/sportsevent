package com.inspius.sportsevent.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.parseDatetimeUTC(
    format: String = "dd MMM yyyy HH:mm", locale: Locale = Locale.US
): String {
    return SimpleDateFormat(format, locale).format(asDate())
}

fun String.asDate(): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    return formatter.parse(this) ?: throw IllegalArgumentException("Input date invalid")
}