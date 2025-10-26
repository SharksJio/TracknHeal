package com.mediassist.tracknheal.data.models

data class DashboardMetricCard(
    val title: String,
    val value: String,
    val subtitle: String,
    val status: HealthStatus,
    val lastUpdated: String
)
