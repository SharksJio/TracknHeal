package com.mediassist.tracknheal.data.database

import com.mediassist.tracknheal.data.models.*
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toInstant

object EntityConverters {
    fun toHealthRecord(entity: HealthRecordEntity, metrics: List<HealthMetricEntity>): HealthRecord {
        return HealthRecord(
            id = entity.id,
            userId = entity.userId,
            dateTime = Instant.fromEpochMilliseconds(entity.dateTime).toLocalDateTime(TimeZone.currentSystemDefault()),
            metrics = metrics.map { toHealthMetric(it) },
            notes = entity.notes,
            createdAt = Instant.fromEpochMilliseconds(entity.createdAt).toLocalDateTime(TimeZone.currentSystemDefault()),
            updatedAt = Instant.fromEpochMilliseconds(entity.updatedAt).toLocalDateTime(TimeZone.currentSystemDefault())
        )
    }

    fun toHealthMetric(entity: HealthMetricEntity): HealthMetric {
        return HealthMetric(
            parameter = ParameterType.valueOf(entity.parameter),
            value = entity.value,
            secondaryValue = entity.secondaryValue,
            unit = MeasurementUnit.valueOf(entity.unit),
            status = HealthStatus.valueOf(entity.status)
        )
    }

    fun fromHealthRecord(record: HealthRecord): HealthRecordEntity {
        return HealthRecordEntity(
            id = record.id,
            userId = record.userId,
            dateTime = record.dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
            notes = record.notes,
            createdAt = record.createdAt.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
            updatedAt = record.updatedAt.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        )
    }

    fun fromHealthMetric(metric: HealthMetric, recordId: String): HealthMetricEntity {
        return HealthMetricEntity(
            recordId = recordId,
            parameter = metric.parameter.name,
            value = metric.value,
            secondaryValue = metric.secondaryValue,
            unit = metric.unit.name,
            status = metric.status.name
        )
    }
}
