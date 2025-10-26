package com.mediassist.tracknheal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mediassist.tracknheal.data.database.HealthDatabase
import com.mediassist.tracknheal.data.repository.HealthRepository
import com.mediassist.tracknheal.ui.navigation.Screen
import com.mediassist.tracknheal.ui.screens.*
import com.mediassist.tracknheal.ui.theme.TracknHealTheme
import com.mediassist.tracknheal.ui.viewmodels.DashboardViewModel
import com.mediassist.tracknheal.ui.viewmodels.ManualEntryViewModel
import com.mediassist.tracknheal.ui.viewmodels.RecordsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = HealthDatabase.getDatabase(applicationContext)
        val repository = HealthRepository(
            recordDao = database.healthRecordDao(),
            metricDao = database.healthMetricDao()
        )

        setContent {
            TracknHealTheme {
                MediAssistApp(repository)
            }
        }
    }
}

@Composable
fun MediAssistApp(repository: HealthRepository) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            val viewModel: DashboardViewModel = viewModel(
                factory = DashboardViewModelFactory(repository)
            )
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToAddRecord = {
                    navController.navigate(Screen.AddRecord.route)
                },
                onNavigateToCharts = {
                    navController.navigate(Screen.Charts.route)
                },
                onNavigateToRecords = {
                    navController.navigate(Screen.Records.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(Screen.AddRecord.route) {
            AddRecordScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToManualEntry = {
                    navController.navigate(Screen.ManualEntry.route)
                }
            )
        }

        composable(Screen.ManualEntry.route) {
            val viewModel: ManualEntryViewModel = viewModel(
                factory = ManualEntryViewModelFactory(repository)
            )
            ManualEntryScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Charts.route) {
            ChartsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Records.route) {
            val viewModel: RecordsViewModel = viewModel(
                factory = RecordsViewModelFactory(repository)
            )
            RecordsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
