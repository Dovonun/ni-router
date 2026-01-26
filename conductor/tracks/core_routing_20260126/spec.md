# Specification: Core Intent Routing

## Overview
This track implements the core functionality of the `ni-router` app. The goal is to create a minimal Android application that acts as an intent router for specific QR code payloads (WiFi, vCard, and Calendar).

## User Stories
- As a user, when I scan a WiFi QR code, I want the system to prompt me to connect to the network without manually entering details.
- As a user, when I scan a vCard QR code, I want to see a prompt to save the contact.
- As a user, when I scan a Calendar QR code, I want to see a prompt to add the event to my calendar.

## Functional Requirements
- **WiFi Routing:** Parse `WIFI:T:<type>;S:<ssid>;P:<password>;;` format and use `WifiNetworkSuggestion` to connect.
- **vCard Routing:** Parse `BEGIN:VCARD` payloads, save to `cacheDir/contacts.vcf`, and share via `FileProvider` with `ACTION_VIEW`.
- **Calendar Routing:** Parse `BEGIN:VEVENT` or `BEGIN:VCALENDAR` payloads, save to `cacheDir/event.ics`, and share via `FileProvider` with `ACTION_VIEW`.
- **No UI:** The app should not display any windows. It should process the intent and finish immediately.
- **Zero Dependencies:** Use only native Android APIs and standard Kotlin libraries.

## Technical Constraints
- Minimum SDK: 29
- Target SDK: 36
- Single Activity: `RouterActivity.kt`
- File Sharing: Use `androidx.core.content.FileProvider`.
