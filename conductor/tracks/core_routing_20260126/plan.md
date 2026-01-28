# Implementation Plan: Core Intent Routing

## Phase 1: Project Scaffolding [checkpoint: f4b4a5e]
- [x] Task: Initialize Android project structure (Gradle files, AndroidManifest.xml) 9636c21
    - [x] Set application name to "Ni-Router" in `AndroidManifest.xml` and `strings.xml`
- [x] Task: Configure `FileProvider` in AndroidManifest.xml 555b5d7
- [x] Task: Create `RouterActivity` skeleton with basic intent handling 72c84ad
- [x] Task: Conductor - User Manual Verification 'Project Scaffolding' (Protocol in workflow.md) f4b4a5e

## Phase 2: Routing Implementation
- [ ] Task: Implement WIFI payload routing
    - [ ] Write unit tests for WIFI parsing and routing logic
    - [ ] Implement WIFI parsing and `WifiNetworkSuggestion` logic
- [ ] Task: Implement VCARD payload routing
    - [ ] Write unit tests for VCARD file generation and intent dispatch
    - [ ] Implement VCARD `FileProvider` logic and `ACTION_VIEW` intent
- [ ] Task: Implement Calendar payload routing
    - [ ] Write unit tests for Calendar file generation and intent dispatch
    - [ ] Implement Calendar `FileProvider` logic and `ACTION_VIEW` intent
- [ ] Task: Conductor - User Manual Verification 'Routing Implementation' (Protocol in workflow.md)

## Phase 3: Polish and Validation
- [ ] Task: Implement "Unsupported format" Toast and silent failure logic
- [ ] Task: Final APK size verification and dependency audit
- [ ] Task: Conductor - User Manual Verification 'Polish and Validation' (Protocol in workflow.md)
