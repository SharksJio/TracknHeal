package com.mediassist.tracknheal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mediassist.tracknheal.ui.components.AppHeader
import com.mediassist.tracknheal.ui.theme.BackgroundGray
import com.mediassist.tracknheal.ui.theme.TextSecondary

@Composable
fun ChartsScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            AppHeader(
                title = "Charts",
                onBackClick = onNavigateBack
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGray)
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Charts visualization will be implemented here",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )
        }
    }
}
