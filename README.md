# Ni-Router

Ni-Router is a minimal, privacy-focused Android app for handling QR code data. It acts as a stateless intent router that takes WiFi, contact, or calendar information from a scan and immediately dispatches it to the relevant system application. No UI, no background services, and no tracking.

## Installation

### APK
1. Navigate to the [Releases](https://github.com/Dovonun/ni-router/releases) page.
2. Download the latest `app-release.apk`.
3. Open the file on your device to install.

### Obtainium
For automated updates via [Obtainium](https://github.com/ImranR04/Obtainium), use this repository URL:
`https://github.com/Dovonun/ni-router`

## Security & Verification

Ni-Router is built and signed automatically via GitHub Actions for transparency and auditability.

### AppVerifier
You can verify the authenticity of the APK using [AppVerifier](https://github.com/stefan-niedermann/AppVerifier).

*   **Application ID:** `com.ni.router`
*   **Certificate SHA-256 Fingerprint:** `C1:31:DC:BD:6E:13:F8:D5:4E:48:25:E6:C2:AD:77:0F:D8:5E:96:F3:E9:A9:C1:36:DA:19:17:09:02:E4:A1:62`

This fingerprint is stable across versions and identifies the publisher.

## Technical Details
- **Zero Dependencies:** Uses only native Android APIs and Kotlin.
- **No Play Services:** Compatible with de-Googled devices.
- **Minimal Footprint:** APK size is typically under 200KB.
- **GrapheneOS Optimized:** Designed to complement the GrapheneOS secure QR scanner.
