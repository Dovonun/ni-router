# Ni-Router

Ni-Router is a minimal, privacy-focused Android app for handling QR code data. It acts as a stateless intent router that takes WiFi, contact, or calendar information from a scan and immediately dispatches it to the relevant system application. No UI, no background services, and no tracking.

## Installation

### APK
1. Navigate to the [Releases](https://github.com/Dovonun/ni-router/releases) page.
2. Download the latest `ni-router-release.apk`.
3. Open the file on your device to install.

### Obtainium
For automated updates via [Obtainium](https://github.com/ImranR98/Obtainium), you can add this repository URL:
`https://github.com/Dovonun/ni-router`

[![Get it on Obtainium](https://raw.githubusercontent.com/ImranR98/Obtainium/main/assets/graphics/badge_obtainium.png)](https://apps.obtainium.imranr.dev/redirect?r=obtainium%3A//app/%7B%22id%22%3A%20%22com.ni.router%22%2C%20%22url%22%3A%20%22https%3A//github.com/Dovonun/ni-router%22%2C%20%22author%22%3A%20%22Dovonun%22%2C%20%22name%22%3A%20%22Ni-Router%22%2C%20%22preferredApkIndex%22%3A%200%2C%20%22additionalSettings%22%3A%20%22%7B%5C%22verifyLatestTag%5C%22%3A%20true%2C%20%5C%22apkFilterRegEx%5C%22%3A%20%5C%22ni-router-release.apk%5C%22%7D%22%2C%20%22overrideSource%22%3A%20null%7D)

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
