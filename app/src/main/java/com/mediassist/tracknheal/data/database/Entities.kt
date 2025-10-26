package com.mediassist.tracknheal.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mediassist.tracknheal.data.models.ParameterType
import com.mediassist.tracknheal.data.models.MeasurementUnit
import com.mediassist.tracknheal.data.models.HealthStatus

@Entity(tableName = "health_records")
data class HealthRecordEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val dateTime: Long, // Unix timestamp in milliseconds
    val notes: String?,
    val createdAt: Long,
    val updatedAt: Long
)

@Entity(tableName = "health_metrics")
data class HealthMetricEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val recordId: String,
    val parameter: String, // ParameterType as string
    val value: Double,
    val secondaryValue: Double?,
    val unit: String, // MeasurementUnit as string
    val status: String // HealthStatus as string
)
