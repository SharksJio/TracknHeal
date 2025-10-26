package com.mediassist.tracknheal.data.models

import kotlinx.datetime.LocalDateTime

data class ChartDataPoint(
    val timestamp: LocalDateTime,
    val value: Double,
    val displayLabel: String
)
