# Specification: Fix CI Build and Trigger Issues

## Overview
This track addresses two critical issues in the CI/CD pipeline:
1.  **Keystore Corruption:** The "Tag number over 30" error suggests the restored keystore file is corrupted, likely by the `echo` command in the restoration step.
2.  **Trigger Issues:** While `v*` should work with `git push origin --tags`, adding manual triggers and verifying the configuration will improve reliability and testability.

## Functional Requirements
### 1. Robust Keystore Restoration
-   Replace `echo "${{ secrets.RELEASE_KEYSTORE_BASE64 }}" | base64 -d > release.keystore` with a more robust method using `printf` to ensure no trailing newlines or character encoding issues affect the binary output.
-   Ensure the restored keystore is placed in a consistent location relative to the build script.

### 2. Workflow Trigger Enhancements
-   Add `workflow_dispatch` to `.github/workflows/build-release.yml` to allow manual build and release triggers from the GitHub UI.
-   Verify the `push: tags: - 'v*'` configuration to ensure it matches the `v0.0.1` format correctly.

## Non-Functional Requirements
-   **Stability:** The build process should be consistent across both automated and manual triggers.
-   **Security:** Ensure that even with manual triggers, secrets are handled securely and never exposed in logs.

## Acceptance Criteria
-   Manual trigger (`workflow_dispatch`) appears in GitHub Actions and successfully runs.
-   The "Tag number over 30" error is resolved, and the release build successfully signs the APK.
-   The workflow triggers correctly on tag pushes matching the `v*` pattern.
