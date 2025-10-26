package com.mediassist.tracknheal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediassist.tracknheal.data.models.*
import com.mediassist.tracknheal.data.repository.HealthRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs

class DashboardViewModel(
    private val repository: HealthRepository
) : ViewModel() {

    private val _metricCards = MutableStateFlow<List<DashboardMetricCard>>(emptyList())
    val metricCards: StateFlow<List<DashboardMetricCard>> = _metricCards.asStateFlow()

    private val _recentNotes = MutableStateFlow<String?>(null)
    val recentNotes: StateFlow<String?> = _recentNotes.asStateFlow()

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            repository.getAllRecords().collect { records ->
                val cards = mutableListOf<DashboardMetricCard>()

                // Get latest metric for each parameter type
                val latestRecords = records.firstOrNull()
                val now = Clock.System.now()

                ParameterType.values().forEach { paramType ->
                    // Find latest metric of this type
                    val latestMetric = records
                        .flatMap { it.metrics }
                        .filter { it.parameter == paramType }
                        .firstOrNull()

                    if (latestMetric != null) {
                        val record = records.first { it.metrics.contains(latestMetric) }
                        val hoursDiff = (now.toEpochMilliseconds() - 
                            record.dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()) / (1000 * 60 * 60)
                        
                        cards.add(
                            DashboardMetricCard(
                                title = paramType.getDisplayName(),
                                value = latestMetric.getFullValueWithUnit(),
                                subtitle = "${hoursDiff.toDouble().let { if (it < 1) "< 1" else it.toInt().toString() }} hours ${HealthMetric.getIdealRange(paramType)}",
                                status = latestMetric.status,
                                lastUpdated = "$hoursDiff hours"
                            )
                        )
                    }
                }

                _metricCards.value = cards

                // Get recent notes
                _recentNotes.value = records.firstOrNull()?.notes
            }
        }
    }
}
