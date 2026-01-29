# Tech Stack

## Development Environment
- **Language:** Kotlin
- **Build System:** Gradle with Kotlin DSL (`build.gradle.kts`)
- **Version Control:** Git
- **CI/CD:** GitHub Actions
    - **JDK:** 17 (Temurin)
    - **Automated Releases:** Signed APKs on tag push (`v*`)
    - **Build Verification:** Linting and build check on Pull Requests

## Android Specifications
- **Minimum SDK:** 29 (Android 10) - Required for `WifiNetworkSuggestion` API.
- **Target SDK:** 36 (Android 16) - Using the latest available target for maximum compatibility.
- **Compile SDK:** 36

## Native APIs & Components
- **Activity:** Standard `android.app.Activity` for the entry point (minimizing overhead).
- **Connectivity:** `WifiManager` and `WifiNetworkSuggestion` for WiFi handling.
- **Data Sharing:** `androidx.core.content.FileProvider` for sharing vCard and Calendar files securely.
- **Intents:** Standard Android Intent system for routing (`ACTION_SEND`, `ACTION_VIEW`).

## Dependencies (Strictly Limited)
- **AndroidX Core:** `androidx.core:core-ktx` (Minimal requirement for `FileProvider` and Kotlin extensions).
- **No Third-Party Libraries:** All parsing and routing must be implemented using native Kotlin/Android APIs.
