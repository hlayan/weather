package com.hlayan.weather.core.ui.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.Locale

fun TemporalAccessor.format(pattern: String): String {
    return DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH).format(this)
}

fun String.asLocalDate(): LocalDate {
    return LocalDate.parse(this)
}

fun String.asLocalDateTime(pattern: String = "yyyy-MM-dd HH:mm"): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH))
}