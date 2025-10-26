package com.mediassist.tracknheal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mediassist.tracknheal.ui.components.AppHeader
import com.mediassist.tracknheal.ui.components.HealthMetricCard
import com.mediassist.tracknheal.ui.theme.BackgroundGray
import com.mediassist.tracknheal.ui.theme.CardBackground
import com.mediassist.tracknheal.ui.theme.PrimaryBlue
import com.mediassist.tracknheal.ui.theme.TextPrimary
import com.mediassist.tracknheal.ui.viewmodels.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToAddRecord: () -> Unit,
    onNavigateToCharts: () -> Unit,
    onNavigateToRecords: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val metricCards by viewModel.metricCards.collectAsState()
    val recentNotes by viewModel.recentNotes.collectAsState()

    Scaffold(
        topBar = {
            AppHeader(
                title = "MediAssist",
                onSettingsClick = onNavigateToSettings
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddRecord,
                containerColor = PrimaryBlue,
                contentColor = CardBackground
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Record"
                )
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
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Greeting
            item {
                Text(
                    text = "Hi, User — This week looks great",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
            }

            // Health Metrics Cards
            items(metricCards) { card ->
                HealthMetricCard(metric = card)
            }

            // Navigation Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onNavigateToCharts,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        )
                    ) {
                        Text("Charts")
                    }

                    Button(
                        onClick = onNavigateToRecords,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        )
                    ) {
                        Text("Records")
                    }
                }
            }

            // Recent Notes
            if (recentNotes != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CardBackground
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Recent Notes",
                                style = MaterialTheme.typography.titleSmall,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = recentNotes ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
