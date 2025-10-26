package com.mediassist.tracknheal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mediassist.tracknheal.data.models.HealthMetric
import com.mediassist.tracknheal.data.models.HealthRecord
import com.mediassist.tracknheal.data.models.HealthStatus
import com.mediassist.tracknheal.ui.components.AppHeader
import com.mediassist.tracknheal.ui.theme.*
import com.mediassist.tracknheal.ui.viewmodels.RecordsViewModel

@Composable
fun RecordsScreen(
    viewModel: RecordsViewModel,
    onNavigateBack: () -> Unit
) {
    val records by viewModel.records.collectAsState()
    val groupedRecords = records.groupBy { it.getFormattedDate() }

    Scaffold(
        topBar = {
            AppHeader(
                title = "Records",
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        if (records.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundGray)
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "No records available",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary
                )
            }
        } else {
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

                groupedRecords.forEach { (date, recordsForDate) ->
                    item {
                        Text(
                            text = date,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(recordsForDate) { record ->
                        RecordCard(record = record)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun RecordCard(record: HealthRecord) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            record.metrics.forEach { metric ->
                MetricRow(metric = metric)
            }

            if (record.notes != null) {
                Divider(modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    text = "Notes: ${record.notes}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
fun MetricRow(metric: HealthMetric) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${metric.parameter.getDisplayName()}:",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
            Text(
                text = metric.getFullValueWithUnit(),
                style = MaterialTheme.typography.bodyLarge,
                color = when (metric.status) {
                    HealthStatus.NORMAL -> StatusNormal
                    HealthStatus.ELEVATED -> StatusElevated
                    HealthStatus.LOW -> StatusLow
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Ideal:",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                text = HealthMetric.getIdealRange(metric.parameter),
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}
