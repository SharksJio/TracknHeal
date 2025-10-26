package com.mediassist.tracknheal.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object AddRecord : Screen("add_record")
    object ManualEntry : Screen("manual_entry")
    object Charts : Screen("charts")
    object Records : Screen("records")
    object Settings : Screen("settings")
}
