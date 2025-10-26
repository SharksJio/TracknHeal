package com.mediassist.tracknheal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediassist.tracknheal.data.models.*
import com.mediassist.tracknheal.data.repository.HealthRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.*

class ManualEntryViewModel(
    private val repository: HealthRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
    val selectedDate: StateFlow<LocalDateTime> = _selectedDate.asStateFlow()

    private val _metrics = MutableStateFlow<List<MetricEntry>>(listOf(MetricEntry()))
    val metrics: StateFlow<List<MetricEntry>> = _metrics.asStateFlow()

    private val _notes = MutableStateFlow("")
    val notes: StateFlow<String> = _notes.asStateFlow()

    private val _saveSuccess = MutableStateFlow<Boolean?>(null)
    val saveSuccess: StateFlow<Boolean?> = _saveSuccess.asStateFlow()

    fun updateDate(date: LocalDateTime) {
        _selectedDate.value = date
    }

    fun updateMetric(index: Int, metric: MetricEntry) {
        val currentMetrics = _metrics.value.toMutableList()
        if (index < currentMetrics.size) {
            currentMetrics[index] = metric
            _metrics.value = currentMetrics
        }
    }

    fun addMetric() {
        _metrics.value = _metrics.value + MetricEntry()
    }

    fun removeMetric(index: Int) {
        if (_metrics.value.size > 1) {
            _metrics.value = _metrics.value.filterIndexed { i, _ -> i != index }
        }
    }

    fun updateNotes(notes: String) {
        _notes.value = notes
    }

    fun saveRecord(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val healthMetrics = _metrics.value.mapNotNull { entry ->
                    if (entry.parameter != null && entry.value.isNotEmpty()) {
                        val value = entry.value.toDoubleOrNull() ?: return@mapNotNull null
                        val secondaryValue = entry.secondaryValue?.toDoubleOrNull()
                        val unit = entry.unit ?: MeasurementUnit.getDefaultUnit(entry.parameter)
                        val status = HealthMetric.calculateStatus(entry.parameter, value, secondaryValue)
                        
                        HealthMetric(
                            parameter = entry.parameter,
                            value = value,
                            secondaryValue = secondaryValue,
                            unit = unit,
                            status = status
                        )
                    } else null
                }

                if (healthMetrics.isNotEmpty()) {
                    repository.createRecord(
                        userId = "default_user",
                        dateTime = _selectedDate.value,
                        metrics = healthMetrics,
                        notes = _notes.value.ifEmpty { null }
                    )
                    _saveSuccess.value = true
                    resetForm()
                    onSuccess()
                } else {
                    _saveSuccess.value = false
                }
            } catch (e: Exception) {
                _saveSuccess.value = false
            }
        }
    }

    private fun resetForm() {
        _selectedDate.value = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        _metrics.value = listOf(MetricEntry())
        _notes.value = ""
    }

    fun clearSaveStatus() {
        _saveSuccess.value = null
    }

    data class MetricEntry(
        val parameter: ParameterType? = null,
        val value: String = "",
        val secondaryValue: String? = null,
        val unit: MeasurementUnit? = null
    )
}
