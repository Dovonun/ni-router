# Ni-Router

Ni-Router is a minimal, privacy-focused Android application designed to act as a stateless intent router for QR code payloads. It extracts data from scanned codes (WiFi, vCard, Calendar) and dispatches them to the appropriate system handlers (WiFi Settings, Contacts, Calendar) without maintaining any UI, persistence, or background services. Designed primarily for GrapheneOS users, it provides a seamless bridge between raw QR data and system actions.

## Installation

### Manual Installation
1. Navigate to the [Releases](https://github.com/Dovonun/ni-router/releases) page.
2. Download the latest `app-release.apk`.
3. Open the APK on your Android device to install.

### Obtainium
For automated updates, you can add this repository URL directly to [Obtainium](https://github.com/ImranR04/Obtainium):
`https://github.com/Dovonun/ni-router`

## Security & Verification

Ni-Router is built and signed automatically via GitHub Actions to ensure build integrity and transparency.

### AppVerifier
You can verify the authenticity of the installed APK using [AppVerifier](https://github.com/stefan-niedermann/AppVerifier).

*   **Application ID:** `com.ni.router`
*   **Certificate SHA-256 Fingerprint:** `PLACEHOLDER_SHA256_FINGERPRINT`

The certificate fingerprint is stable across all versions and represents the unique publisher identity.

## Technical Notes
- **Zero Dependencies:** Uses only native Android APIs and standard Kotlin libraries.
- **No Play Services:** Does not require or use Google Play Services.
- **Minimal Footprint:** Optimized for size and performance (APK size < 200KB).
- **GrapheneOS Optimized:** Designed to complement the GrapheneOS secure QR scanner.
