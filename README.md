# MediAssist - TracknHeal

A personal health tracking mobile application built with Android Kotlin and Jetpack Compose that helps users monitor and manage their vital health metrics including glucose levels, blood pressure, cholesterol, and HbA1c.

## Features

### Phase 1 - MVP (Implemented)
- ✅ Dashboard with health metrics cards
- ✅ Manual entry form for health data
- ✅ Records list view with historical data
- ✅ Basic data persistence using Room database
- ✅ Navigation between screens
- ✅ Material Design 3 UI
- ✅ Settings screen structure
- ✅ Charts screen placeholder

### Core Functionality
- **Dashboard**: View current health metrics with status indicators (Normal, Elevated, Low)
- **Manual Entry**: Form-based data input with validation for multiple health parameters
- **Records**: Chronological list of all health records grouped by date
- **Settings**: Configuration options including export, backup, security, and about

### Data Models
- Health Records with multiple metrics per entry
- Support for Glucose, Blood Pressure, Cholesterol, and HbA1c
- Automatic status calculation based on ideal health ranges
- Date/time tracking for all entries

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material Design 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite)
- **Navigation**: Jetpack Navigation Compose
- **Async**: Kotlin Coroutines + Flow
- **Date/Time**: kotlinx-datetime

## Requirements

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34

## Build Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/SharksJio/TracknHeal.git
   cd TracknHeal
   ```

2. Open the project in Android Studio (Arctic Fox or later)

3. Sync Gradle files

4. Run the app on an emulator or physical device

## Project Structure

```
app/src/main/java/com/mediassist/tracknheal/
├── data/
│   ├── database/      # Room database, DAOs, entities
│   ├── models/        # Data models and enums
│   └── repository/    # Repository layer
├── ui/
│   ├── components/    # Reusable UI components
│   ├── navigation/    # Navigation setup
│   ├── screens/       # Screen composables
│   ├── theme/         # App theme and styling
│   └── viewmodels/    # ViewModels
└── MainActivity.kt    # Main entry point
```

## Color Palette

- **Primary Blue**: `#2563EB` - Headers, CTAs, active elements
- **Hover Blue**: `#1D4ED8`
- **Background Gray**: `#F9FAFB`
- **Card Background**: `#FFFFFF`
- **Text Primary**: `#111827`
- **Text Secondary**: `#6B7280`
- **Status Colors**:
  - Normal: `#10B981` (Green)
  - Elevated: `#F97316` (Orange)
  - Low: `#EF4444` (Red)

## Health Ranges Reference

| Parameter | Normal Range | Elevated | Low |
|-----------|-------------|----------|-----|
| Glucose (Fasting) | 70-100 mg/dL | >100 mg/dL | <70 mg/dL |
| Blood Pressure | <120/80 mmHg | 120-139/80-89 | <90/60 |
| Cholesterol | <200 mg/dL | 200-239 mg/dL | - |
| HbA1c | <5.7% | 5.7-6.4% | - |

## Future Enhancements

### Phase 2
- Interactive charts with trend analysis
- CSV export functionality
- Time period filtering

### Phase 3
- Camera/OCR integration for scanning medical reports
- Biometric security (PIN/Fingerprint)
- Backup and restore functionality
- Cloud sync

### Phase 4
- Multi-user support
- Medication tracking
- Appointment reminders
- Share reports with healthcare providers
- AI-powered health insights

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Medical Disclaimer

This app is for informational purposes only and is not intended to replace professional medical advice, diagnosis, or treatment. Always seek the advice of your physician or other qualified health provider with any questions you may have regarding a medical condition.

## Privacy

All health data is stored locally on your device by default. The app does not share data with third parties without explicit user consent.
