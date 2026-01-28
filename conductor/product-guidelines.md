# Product Guidelines

## Error Handling
- **Minimal Feedback:** If a payload is invalid or unsupported, show a brief, native Android Toast message (e.g., "Unsupported QR format") before calling `finish()` to provide minimal feedback while maintaining a low-profile UI.

## Data Persistence & Privacy
- **Cache-Only Volatility:** Only use the application's `cacheDir` for temporary files required by `FileProvider` (such as vCard or ICS files). Ensure these files are temporary and volatile, strictly avoiding any long-term storage or tracking.
- **Zero Network Access:** The application must never request or use network permissions, ensuring all data processing remains strictly on the device.

## Code Philosophy & Parsing
- **Native Minimalism:** Adhere to a "no dependency" rule by using standard Kotlin String manipulation and Regex for all parsing tasks (WIFI, vCard, Calendar).
- **Modular Minimalism:** Keep parsing and routing logic modularized (e.g., `WifiRouter.kt`, `VCardRouter.kt`) for clarity, while maintaining a zero-dependency, lightweight footprint.
- **System-First Execution:** The app's role is strictly as a translator; once a system intent is dispatched, the app must immediately terminate.
