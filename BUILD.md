# Build Instructions

## Prerequisites

1. **Android Studio** (Arctic Fox or later recommended)
   - Download from: https://developer.android.com/studio

2. **JDK 17** or higher
   - Android Studio includes a bundled JDK

3. **Android SDK**
   - Min SDK: 24 (Android 7.0)
   - Target SDK: 34 (Android 14)
   - Compile SDK: 34

## Building the Project

### Using Android Studio (Recommended)

1. **Open the Project**
   ```
   File > Open > Select the TracknHeal directory
   ```

2. **Sync Gradle Files**
   - Android Studio will automatically prompt to sync Gradle files
   - Click "Sync Now" when prompted
   - Wait for dependencies to download

3. **Build the Project**
   ```
   Build > Make Project
   or press Ctrl+F9 (Windows/Linux) / Cmd+F9 (Mac)
   ```

4. **Run on Emulator or Device**
   ```
   Run > Run 'app'
   or press Shift+F10 (Windows/Linux) / Ctrl+R (Mac)
   ```

### Using Command Line

1. **Generate Gradle Wrapper** (if not present)
   ```bash
   gradle wrapper --gradle-version 8.2
   ```

2. **Build the Project**
   ```bash
   ./gradlew build
   ```

3. **Run Tests**
   ```bash
   ./gradlew test
   ```

4. **Build Debug APK**
   ```bash
   ./gradlew assembleDebug
   ```
   
   The APK will be located at:
   `app/build/outputs/apk/debug/app-debug.apk`

5. **Install on Connected Device**
   ```bash
   ./gradlew installDebug
   ```

## Project Structure

```
TracknHeal/
├── app/
│   ├── build.gradle.kts           # App module build configuration
│   ├── proguard-rules.pro         # ProGuard rules
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/mediassist/tracknheal/
│           │   ├── data/           # Data layer
│           │   │   ├── database/   # Room database
│           │   │   ├── models/     # Data models
│           │   │   └── repository/ # Repository layer
│           │   ├── ui/             # UI layer
│           │   │   ├── components/ # Reusable components
│           │   │   ├── navigation/ # Navigation setup
│           │   │   ├── screens/    # Screen composables
│           │   │   ├── theme/      # App theme
│           │   │   └── viewmodels/ # ViewModels
│           │   ├── MainActivity.kt
│           │   └── ViewModelFactories.kt
│           └── res/                # Resources
│               ├── values/
│               │   ├── strings.xml
│               │   ├── colors.xml
│               │   └── themes.xml
│               └── xml/
│                   ├── backup_rules.xml
│                   └── data_extraction_rules.xml
├── build.gradle.kts               # Root build configuration
├── settings.gradle.kts            # Gradle settings
├── gradle.properties              # Gradle properties
└── README.md                      # Project documentation
```

## Dependencies

The project uses the following major dependencies:

- **Jetpack Compose**: Modern Android UI toolkit
- **Room**: Database persistence library
- **Navigation Compose**: Navigation for Compose
- **kotlinx-datetime**: Date/time handling
- **Coroutines & Flow**: Asynchronous programming
- **Material 3**: Material Design 3 components
- **CameraX**: Camera functionality (for future OCR feature)
- **ML Kit**: Text recognition (for future OCR feature)
- **Biometric**: Biometric authentication (for future security feature)

## Troubleshooting

### Gradle Sync Failed

1. Check internet connection
2. Ensure JDK 17 or higher is installed
3. Clear Gradle cache:
   ```bash
   ./gradlew clean
   rm -rf .gradle
   ```

### Build Failed

1. Check Android SDK is properly installed
2. Update build tools in Android Studio
3. Verify compileSdk and targetSdk versions match your SDK

### App Crashes on Launch

1. Check logcat for error messages
2. Ensure minimum Android version is 7.0 (API 24) or higher
3. Verify permissions in AndroidManifest.xml

## Testing

The project includes:
- Unit tests for data models and business logic
- Instrumented tests for database operations
- UI tests for composable screens (to be added)

Run tests:
```bash
./gradlew test           # Unit tests
./gradlew connectedAndroidTest  # Instrumented tests
```

## Generating APK

### Debug APK
```bash
./gradlew assembleDebug
```

### Release APK
1. Create keystore for signing
2. Configure signing in `app/build.gradle.kts`
3. Build release APK:
```bash
./gradlew assembleRelease
```

## IDE Configuration

### Android Studio Settings

1. **Code Style**: Use default Kotlin style
2. **Gradle JVM**: Use embedded JDK (recommended)
3. **Build Variants**: Debug (for development)

### Recommended Plugins

- Kotlin
- Android
- Gradle
- Compose support (built-in)

## Known Issues

- Network restrictions may prevent automatic dependency download in restricted environments
- First build may take several minutes to download all dependencies
- Emulator requires hardware acceleration for optimal performance

## Support

For issues or questions:
- Check the README.md for feature documentation
- Review Android Developer documentation: https://developer.android.com
- Jetpack Compose documentation: https://developer.android.com/jetpack/compose
