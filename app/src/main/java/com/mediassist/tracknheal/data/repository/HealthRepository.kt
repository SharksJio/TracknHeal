package com.mediassist.tracknheal.data.repository

import com.mediassist.tracknheal.data.database.*
import com.mediassist.tracknheal.data.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID

class HealthRepository(
    private val recordDao: HealthRecordDao,
    private val metricDao: HealthMetricDao
) {
    fun getAllRecords(): Flow<List<HealthRecord>> {
        return recordDao.getAllRecords().map { entities ->
            entities.map { entity ->
                val metrics = metricDao.getMetricsForRecord(entity.id)
                EntityConverters.toHealthRecord(entity, metrics)
            }
        }
    }

    suspend fun getRecordById(recordId: String): HealthRecord? {
        val entity = recordDao.getRecordById(recordId) ?: return null
        val metrics = metricDao.getMetricsForRecord(recordId)
        return EntityConverters.toHealthRecord(entity, metrics)
    }

    fun getRecordsSince(startTimeMillis: Long): Flow<List<HealthRecord>> {
        return recordDao.getRecordsSince(startTimeMillis).map { entities ->
            entities.map { entity ->
                val metrics = metricDao.getMetricsForRecord(entity.id)
                EntityConverters.toHealthRecord(entity, metrics)
            }
        }
    }

    suspend fun insertRecord(record: HealthRecord) {
        val recordEntity = EntityConverters.fromHealthRecord(record)
        recordDao.insertRecord(recordEntity)
        
        val metricEntities = record.metrics.map { metric ->
            EntityConverters.fromHealthMetric(metric, record.id)
        }
        metricDao.insertMetrics(metricEntities)
    }

    suspend fun createRecord(
        userId: String,
        dateTime: kotlinx.datetime.LocalDateTime,
        metrics: List<HealthMetric>,
        notes: String?
    ): HealthRecord {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val record = HealthRecord(
            id = UUID.randomUUID().toString(),
            userId = userId,
            dateTime = dateTime,
            metrics = metrics,
            notes = notes,
            createdAt = now,
            updatedAt = now
        )
        insertRecord(record)
        return record
    }

    suspend fun deleteRecord(recordId: String) {
        val entity = recordDao.getRecordById(recordId)
        if (entity != null) {
            metricDao.deleteMetricsForRecord(recordId)
            recordDao.deleteRecord(entity)
        }
    }

    suspend fun getLatestMetricByParameter(parameter: ParameterType): HealthMetric? {
        val entity = metricDao.getLatestMetricByParameter(parameter.name)
        return entity?.let { EntityConverters.toHealthMetric(it) }
    }

    suspend fun getMetricsForCharts(parameter: ParameterType, startTimeMillis: Long): List<Pair<HealthRecordEntity, HealthMetricEntity>> {
        val metrics = metricDao.getMetricsByParameterSince(parameter.name, startTimeMillis)
        return metrics.mapNotNull { metric ->
            val record = recordDao.getRecordById(metric.recordId)
            if (record != null) Pair(record, metric) else null
        }
    }

    suspend fun deleteAllData() {
        metricDao.deleteAllMetrics()
        recordDao.deleteAllRecords()
    }
}
