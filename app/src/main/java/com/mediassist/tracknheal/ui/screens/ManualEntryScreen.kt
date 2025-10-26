package com.mediassist.tracknheal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mediassist.tracknheal.data.models.MeasurementUnit
import com.mediassist.tracknheal.data.models.ParameterType
import com.mediassist.tracknheal.ui.components.AppHeader
import com.mediassist.tracknheal.ui.theme.*
import com.mediassist.tracknheal.ui.viewmodels.ManualEntryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualEntryScreen(
    viewModel: ManualEntryViewModel,
    onNavigateBack: () -> Unit
) {
    val metrics by viewModel.metrics.collectAsState()
    val notes by viewModel.notes.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()

    LaunchedEffect(saveSuccess) {
        if (saveSuccess == true) {
            onNavigateBack()
            viewModel.clearSaveStatus()
        }
    }

    Scaffold(
        topBar = {
            AppHeader(
                title = "Manual Entry",
                onBackClick = onNavigateBack
            )
        },
        bottomBar = {
            Button(
                onClick = { viewModel.saveRecord(onSuccess = onNavigateBack) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue
                )
            ) {
                Text("Save Record", modifier = Modifier.padding(8.dp))
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGray)
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Date field
            item {
                OutlinedTextField(
                    value = viewModel.selectedDate.value.toString(),
                    onValueChange = {},
                    label = { Text("Date") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = CardBackground
                    )
                )
            }

            // Metric entries
            itemsIndexed(metrics) { index, metric ->
                MetricEntryCard(
                    metric = metric,
                    onMetricChange = { updatedMetric ->
                        viewModel.updateMetric(index, updatedMetric)
                    },
                    onRemove = if (metrics.size > 1) {
                        { viewModel.removeMetric(index) }
                    } else null
                )
            }

            // Add another parameter button
            item {
                TextButton(
                    onClick = { viewModel.addMetric() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("+ Add Another Parameter", color = PrimaryBlue)
                }
            }

            // Notes field
            item {
                OutlinedTextField(
                    value = notes,
                    onValueChange = { viewModel.updateNotes(it) },
                    label = { Text("Notes (Optional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 4,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = CardBackground
                    )
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetricEntryCard(
    metric: ManualEntryViewModel.MetricEntry,
    onMetricChange: (ManualEntryViewModel.MetricEntry) -> Unit,
    onRemove: (() -> Unit)?
) {
    var parameterExpanded by remember { mutableStateOf(false) }
    var unitExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Parameter dropdown
            ExposedDropdownMenuBox(
                expanded = parameterExpanded,
                onExpandedChange = { parameterExpanded = it }
            ) {
                OutlinedTextField(
                    value = metric.parameter?.getDisplayName() ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Parameter") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = parameterExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                )

                ExposedDropdownMenu(
                    expanded = parameterExpanded,
                    onDismissRequest = { parameterExpanded = false }
                ) {
                    ParameterType.values().forEach { param ->
                        DropdownMenuItem(
                            text = { Text(param.getDisplayName()) },
                            onClick = {
                                onMetricChange(
                                    metric.copy(
                                        parameter = param,
                                        unit = MeasurementUnit.getDefaultUnit(param)
                                    )
                                )
                                parameterExpanded = false
                            }
                        )
                    }
                }
            }

            // Value field(s)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = metric.value,
                    onValueChange = { onMetricChange(metric.copy(value = it)) },
                    label = { Text("Value") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                // For Blood Pressure, show secondary value field
                if (metric.parameter == ParameterType.BLOOD_PRESSURE) {
                    OutlinedTextField(
                        value = metric.secondaryValue ?: "",
                        onValueChange = { onMetricChange(metric.copy(secondaryValue = it)) },
                        label = { Text("Diastolic") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }

            // Unit dropdown
            ExposedDropdownMenuBox(
                expanded = unitExpanded,
                onExpandedChange = { unitExpanded = it }
            ) {
                OutlinedTextField(
                    value = metric.unit?.getDisplayName() ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Unit") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = unitExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                )

                ExposedDropdownMenu(
                    expanded = unitExpanded,
                    onDismissRequest = { unitExpanded = false }
                ) {
                    MeasurementUnit.values().forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit.getDisplayName()) },
                            onClick = {
                                onMetricChange(metric.copy(unit = unit))
                                unitExpanded = false
                            }
                        )
                    }
                }
            }

            // Remove button
            if (onRemove != null) {
                TextButton(
                    onClick = onRemove,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Remove", color = StatusLow)
                }
            }
        }
    }
}
