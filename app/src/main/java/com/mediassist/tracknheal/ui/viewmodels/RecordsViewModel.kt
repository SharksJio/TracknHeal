package com.mediassist.tracknheal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mediassist.tracknheal.data.models.HealthRecord
import com.mediassist.tracknheal.data.repository.HealthRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RecordsViewModel(
    private val repository: HealthRepository
) : ViewModel() {

    private val _records = MutableStateFlow<List<HealthRecord>>(emptyList())
    val records: StateFlow<List<HealthRecord>> = _records.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadRecords()
    }

    fun loadRecords() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllRecords().collect { records ->
                _records.value = records
                _isLoading.value = false
            }
        }
    }

    fun getRecordsGroupedByDate(): Map<String, List<HealthRecord>> {
        return _records.value.groupBy { it.getFormattedDate() }
    }
}
