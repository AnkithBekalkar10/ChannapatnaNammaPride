# Channapatna - Namma Pride Android App

This workspace now contains a Kotlin Android app scaffolded from the SOP in `README.md`.

## What is implemented

- Kotlin Android project with Jetpack Compose and Material 3.
- Bottom navigation for Home, Verify, Catalog, Workshop Map, and Learn.
- 6-digit toy verification flow with authentic and invalid states.
- Sample toy, artisan, workshop, and education data behind a repository interface.
- Catalog filtering by toy category.
- Maker profiles and workshop location metadata.
- Styled workshop map preview with directions links.
- Heritage education process screen.
- Firebase rules and sample seed data.
- English/Kannada resource foundation.
- Unit tests for the sample verification repository.

## How to open

Open this folder in Android Studio:

```text
C:\Users\ankit\OneDrive\Desktop\MM Project
```

Let Android Studio sync Gradle. The shell environment used by Codex does not currently have `gradle`, `kotlinc`, or `ANDROID_HOME` available, so command-line builds could not be run here.

## Next production steps

- Add `google-services.json` to `app/src/main/` after creating the Firebase Android app.
- Apply the Google Services Gradle plugin after adding `google-services.json`.
- Implement Firebase-backed repositories using the existing `ChannapatnaRepository` interface.
- Add Gemini story generation behind the same verification result flow.
- Replace vector/color-block toy previews with real Firebase Storage images.
