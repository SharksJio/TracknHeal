package com.mediassist.tracknheal.data.models

import kotlinx.datetime.LocalDateTime

data class HealthRecord(
    val id: String,
    val userId: String,
    val dateTime: LocalDateTime,
    val metrics: List<HealthMetric>,
    val notes: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    fun getFormattedDate(): String {
        val month = when (dateTime.monthNumber) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> ""
        }
        return "${dateTime.dayOfMonth} $month ${dateTime.year}"
    }

    fun getFormattedTime(): String {
        val hour = if (dateTime.hour == 0) 12 else if (dateTime.hour > 12) dateTime.hour - 12 else dateTime.hour
        val amPm = if (dateTime.hour < 12) "AM" else "PM"
        return String.format("%02d:%02d %s", hour, dateTime.minute, amPm)
    }
}
