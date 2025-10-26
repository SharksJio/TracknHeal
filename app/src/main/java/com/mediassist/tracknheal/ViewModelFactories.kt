package com.mediassist.tracknheal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mediassist.tracknheal.data.repository.HealthRepository
import com.mediassist.tracknheal.ui.viewmodels.DashboardViewModel
import com.mediassist.tracknheal.ui.viewmodels.ManualEntryViewModel
import com.mediassist.tracknheal.ui.viewmodels.RecordsViewModel

class DashboardViewModelFactory(
    private val repository: HealthRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ManualEntryViewModelFactory(
    private val repository: HealthRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManualEntryViewModel::class.java)) {
            return ManualEntryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class RecordsViewModelFactory(
    private val repository: HealthRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordsViewModel::class.java)) {
            return RecordsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
