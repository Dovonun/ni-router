# Ni-Router

Ni-Router is a tiny, privacy-first Android app that handles QR code data without any fuss. It acts as a stateless "middleman" that takes WiFi, contact, or calendar info from a scan and immediately hands it off to the right system app—then it disappears. No UI, no background services, and no tracking.

## Installation

### Get the APK
1. Head over to the [Releases](https://github.com/Dovonun/ni-router/releases) page.
2. Download the latest `app-release.apk`.
3. Open it on your phone to install.

### Obtainium
If you use [Obtainium](https://github.com/ImranR04/Obtainium) for updates, just add this repo URL:
`https://github.com/Dovonun/ni-router`

## Security & Verification

Ni-Router is built and signed automatically via GitHub Actions so the process is transparent and auditable.

### AppVerifier
You can verify that your APK is the real deal using [AppVerifier](https://github.com/stefan-niedermann/AppVerifier).

*   **Application ID:** `com.ni.router`
*   **Certificate SHA-256 Fingerprint:** `PLACEHOLDER_SHA256_FINGERPRINT`

This fingerprint stays the same across versions and confirms the app came from this project.

## Tech Stuff
- **Zero Dependencies:** Just native Android APIs and Kotlin.
- **No Play Services:** Works perfectly on "de-Googled" devices.
- **Tiny APK:** Usually under 200KB.
- **Built for GrapheneOS:** The perfect companion for the GrapheneOS secure QR scanner.
