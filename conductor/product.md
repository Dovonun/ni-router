# Initial Concept

App Name: Ni-Router

Goal:
Build a minimal Android app that acts only as an intent router for QR payloads.
No UI, no persistence, no background services, no dependencies, one Kotlin file if possible.

Language:
- Kotlin
- Native Android APIs only

App structure:
- One Activity: RouterActivity.kt
- No XML layouts
- No fragments
- No resources except AndroidManifest
- Finish immediately after dispatching an intent

Manifest:
- Single activity
- exported = true
- Intent filter:
    - ACTION_SEND
    - CATEGORY_DEFAULT
    - mimeType = "text/*"

Permissions:
- ACCESS_WIFI_STATE
- CHANGE_WIFI_STATE (only if required by API level)

Activity flow:

1. onCreate:
   - Receive intent
   - Extract payload from Intent.dataString (ACTION_VIEW) or Intent.EXTRA_TEXT (ACTION_SEND)
   - If null → finish
   - Trim and normalize text
   - Call handleText(text)

2. onResume:
   - If waiting for WiFi state change, check and re-trigger routing once enabled.

2. handleText(text):
   - If text starts with "WIFI:" → handleWifi(text)
   - Else if text starts with "BEGIN:VCARD" → handleVCard(text)
   - Else if text contains "BEGIN:VEVENT" or "BEGIN:VCALENDAR" → handleCalendar(text)
   - Else → finish

Handlers:

handleWifi(text):
- Parse format:
    WIFI:T:<type>;S:<ssid>;P:<password>;;
- Extract:
    - SSID (S)
    - Password (P)
    - Type (T)
- Use Settings.ACTION_WIFI_ADD_NETWORKS (API 30+) for stateless saved network experience.
- Use Settings.Panel.ACTION_WIFI if WiFi is disabled to prompt user activation.
- Fallback to addNetworkSuggestions for API 29.
- finish()

handleVCard(text):
- Try to parse basic info (FN, TEL, EMAIL, ORG, TITLE, ADR) for ACTION_INSERT to open contact editor.
- Fallback to file-based import via cacheDir and FileProvider with ACTION_VIEW if structured parsing fails.
- finish()

handleCalendar(text):
- Write text to:
    cacheDir/event.ics
- Create content URI via FileProvider
- Create intent:
    ACTION_VIEW
    type = "text/calendar"
    FLAG_GRANT_READ_URI_PERMISSION
- startActivity(intent)
- finish()

Design rules:
- No UI
- No logging
- No analytics
- No network access
- No storage outside cacheDir
- No dependency libraries
- One file for all logic
- App exists only to translate QR payload → correct Android intent → exit

Initial supported formats:
- WIFI
- VCARD
- VEVENT / VCALENDAR

Philosophy:
Camera decodes.
This app routes.
Android executes.
Then the app disappears.

## Target Audience
- General Android users looking for a no-frills, privacy-focused QR action handler.

## Core Value Proposition
- **Seamless Integration:** Designed to be the perfect target for the GrapheneOS QR-code reader.
- **Privacy First:** Handle the desired action without any network or external storage access.
- **Pure Utility:** Creates a simple, secure, dependency-free bridge between raw text data and system action.

## Key Success Metrics
- **Minimal Footprint:** Extremely small APK size (aiming for < 50KB).