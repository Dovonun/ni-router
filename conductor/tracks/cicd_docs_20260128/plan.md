# Implementation Plan: CI/CD and Documentation

## Phase 1: Gradle & Signing Configuration [checkpoint: ce7d884]
- [x] Task: Update `app/build.gradle.kts` to support environment-variable-based signing 70adb58
    - [x] Add `signingConfigs` block that reads from `System.getenv()`
    - [x] Configure `release` build type to use the new `signingConfig`
- [x] Task: Configure build for reproducibility/determinism b4d9892
    - [x] Disable timestamps in APK ZIP entries via Gradle properties
    - [x] Ensure release build strips debug symbols and omits build metadata
- [x] Task: Conductor - User Manual Verification 'Gradle & Signing Configuration' (Protocol in workflow.md) ce7d884

## Phase 2: GitHub Actions Workflow
- [ ] Task: Create GitHub Actions workflow file `.github/workflows/build-release.yml`
    - [ ] Implement `build` job for PRs (Lint + Build only)
    - [ ] Implement `release` job for Tags (Lint + Signed Build + GitHub Release)
    - [ ] Add keystore restoration step (`base64 -d`) from secrets
- [ ] Task: Write failing test/verification for workflow triggers
    - [ ] Push a branch to verify PR workflow triggers correctly (Red Phase)
- [ ] Task: Conductor - User Manual Verification 'GitHub Actions Workflow' (Protocol in workflow.md)

## Phase 3: Documentation
- [ ] Task: Create `README.md` with technical specifications
    - [ ] Add one-paragraph app summary
    - [ ] Add Obtainium and manual installation instructions
    - [ ] Add verification section with AppVerifier instructions and Fingerprint placeholders
    - [ ] Add GrapheneOS and No-Play-Services notes
- [ ] Task: Conductor - User Manual Verification 'Documentation' (Protocol in workflow.md)
