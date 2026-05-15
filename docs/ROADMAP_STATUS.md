# Feature Roadmap Status

## Implemented

- Kotlin Android app project.
- Jetpack Compose UI.
- Material 3 visual system.
- Home, Verify, Catalog, Workshop Map, and Learn screens.
- 6-digit toy verification flow.
- Sample repository with toys, artisans, workshops, and educational content.
- Workshop map preview with directions intent.
- Firebase rules files.
- Firebase seed data file.
- Firebase Firestore, Maps, Gemini, and Coil dependencies declared.
- BuildConfig hooks for `GEMINI_API_KEY` and `MAPS_API_KEY`.
- English and Kannada string resource foundation.
- Basic unit tests for verification data.
- Debug build verified with Gradle.

## Partially Implemented

- Firebase: Firestore repository wiring is implemented with sample-data fallback.
- Gemini: dependency and API key BuildConfig field are present; live story generation requires a Gemini key and repository swap.
- Maps: dependency and API key BuildConfig field are present; native Google Map composable can replace the preview after Maps key setup.
- Localization: resource files and helper exist; UI hardcoded text still needs migration to string resources for full runtime switching.

## Requires Your Credentials Or Console Setup

- `app/src/main/google-services.json`
- Firestore database enabled in Firebase Console.
- Firebase Storage enabled in Firebase Console.
- Google Maps SDK enabled for the Android package.
- Gemini API key added to `local.properties`.
- Real image uploads for toy, artisan, education, and workshop assets.

## Recommended Next Build Steps

1. Add `google-services.json`.
2. Apply the Google Services Gradle plugin in `app/build.gradle.kts`.
3. Build once in Android Studio.
4. Replace the sample repository with a Firebase-backed repository.
5. Add real image URLs and Coil image loading.
6. Turn the map preview into a live Google Map.
7. Replace static story text with Gemini-generated stories.
8. Finish UI strings migration to Android resources.
