package com.mediassist.tracknheal.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthRecordDao {
    @Query("SELECT * FROM health_records ORDER BY dateTime DESC")
    fun getAllRecords(): Flow<List<HealthRecordEntity>>

    @Query("SELECT * FROM health_records WHERE id = :recordId")
    suspend fun getRecordById(recordId: String): HealthRecordEntity?

    @Query("SELECT * FROM health_records WHERE dateTime >= :startTime ORDER BY dateTime DESC")
    fun getRecordsSince(startTime: Long): Flow<List<HealthRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: HealthRecordEntity)

    @Delete
    suspend fun deleteRecord(record: HealthRecordEntity)

    @Query("DELETE FROM health_records")
    suspend fun deleteAllRecords()
}

@Dao
interface HealthMetricDao {
    @Query("SELECT * FROM health_metrics WHERE recordId = :recordId")
    suspend fun getMetricsForRecord(recordId: String): List<HealthMetricEntity>

    @Query("SELECT * FROM health_metrics WHERE recordId IN (:recordIds)")
    suspend fun getMetricsForRecords(recordIds: List<String>): List<HealthMetricEntity>

    @Query("SELECT * FROM health_metrics WHERE parameter = :parameter AND recordId IN (SELECT id FROM health_records WHERE dateTime >= :startTime) ORDER BY recordId")
    suspend fun getMetricsByParameterSince(parameter: String, startTime: Long): List<HealthMetricEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetric(metric: HealthMetricEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetrics(metrics: List<HealthMetricEntity>)

    @Query("DELETE FROM health_metrics WHERE recordId = :recordId")
    suspend fun deleteMetricsForRecord(recordId: String)

    @Query("DELETE FROM health_metrics")
    suspend fun deleteAllMetrics()

    @Query("SELECT * FROM health_metrics WHERE parameter = :parameter ORDER BY recordId DESC LIMIT 1")
    suspend fun getLatestMetricByParameter(parameter: String): HealthMetricEntity?
}
