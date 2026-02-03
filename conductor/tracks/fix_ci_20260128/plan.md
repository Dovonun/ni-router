# Implementation Plan: Fix CI Build and Trigger Issues

## Phase 1: Workflow Hardening
- [x] Task: Update `.github/workflows/build-release.yml` for robust restoration and triggers 0483ce0
    - [x] Add `workflow_dispatch:` to the `on` block
    - [x] Replace `echo` with `printf "%s"` in the `Restore Keystore` step to prevent binary corruption
    - [x] Ensure `RELEASE_STORE_FILE` path in the `env` block matches the restoration location
- [ ] Task: Conductor - User Manual Verification 'Workflow Hardening' (Protocol in workflow.md)

## Phase 2: Verification and Cleanup
- [ ] Task: Test manual trigger via GitHub UI (Red/Green Phase)
- [ ] Task: Verify tag trigger with a new test tag (e.g., `v0.0.2-test`)
- [ ] Task: Conductor - User Manual Verification 'Verification and Cleanup' (Protocol in workflow.md)
