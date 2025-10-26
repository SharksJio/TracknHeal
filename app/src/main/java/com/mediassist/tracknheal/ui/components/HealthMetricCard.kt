package com.mediassist.tracknheal.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mediassist.tracknheal.data.models.DashboardMetricCard
import com.mediassist.tracknheal.data.models.HealthStatus
import com.mediassist.tracknheal.ui.theme.*

@Composable
fun HealthMetricCard(
    metric: DashboardMetricCard,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
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
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = metric.title,
                style = MaterialTheme.typography.titleSmall,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Value
            Text(
                text = metric.value,
                style = MaterialTheme.typography.bodyLarge,
                color = when (metric.status) {
                    HealthStatus.NORMAL -> StatusNormal
                    HealthStatus.ELEVATED -> StatusElevated
                    HealthStatus.LOW -> StatusLow
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtitle
            Text(
                text = metric.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}
