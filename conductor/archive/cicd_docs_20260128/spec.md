# Specification: CI/CD and Documentation

## Overview
This track implements a robust CI/CD pipeline using GitHub Actions for automated building, signing, and releasing of the Ni-Router APK. It also includes comprehensive project documentation via a technical README.md tailored for privacy-focused users (GrapheneOS/Obtainium).

## Functional Requirements
### 1. GitHub Actions Workflow
- **Triggers:**
    - On Pull Requests: Run linting and build verification (no signing).
    - On Tag Push (`v*`): Run linting, build signed release APK, and create a GitHub Release with the APK asset.
- **Build Environment:**
    - Java 17.
    - Fixed Gradle wrapper.
- **Signing Process:**
    - Restore keystore from `RELEASE_KEYSTORE_BASE64` secret.
    - Use environment variables for `RELEASE_STORE_FILE`, `RELEASE_STORE_PASSWORD`, `RELEASE_KEY_ALIAS`, and `RELEASE_KEY_PASSWORD`.
- **Reproducibility:**
    - Configure Gradle to minimize non-determinism (no timestamps in APK where possible, no debug symbols, stripped build metadata).
- **Artifacts:**
    - Upload the signed `app-release.apk` to GitHub Releases.

### 2. Documentation (README.md)
- **App Description:** Concise technical overview of the intent routing purpose.
- **Installation:** Instructions for manual APK install and Obtainium integration.
- **Security & Verification:**
    - Instructions for AppVerifier.
    - Placeholders for Application ID and stable SHA-256 certificate fingerprint.
- **Environment:**
    - Explicit mention of GitHub Actions automated signing.
    - Confirmation of zero Play Services dependencies.
    - Mention of GrapheneOS optimization.

## Non-Functional Requirements
- **Security:** No secrets or keystores committed to the repository.
- **Auditability:** Workflow should be clear and minimal to facilitate easy review of the build process.
- **Lightweight:** Maintain the project's minimal footprint philosophy.

## Acceptance Criteria
- GitHub Actions workflow successfully passes for PRs.
- GitHub Actions workflow successfully signs and creates a Release on tag push.
- README.md exists and contains all required technical sections and placeholders.
