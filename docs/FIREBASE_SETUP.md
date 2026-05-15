# Firebase Setup

Use this when you are ready to connect the app to live data.

## 1. Create Firebase Project

1. Create a Firebase project named `channapatna-pride`.
2. Add an Android app with package name `com.channapatna.nammapride`.
3. Download `google-services.json`.
4. Place it at either valid Android app module location:

```text
app/google-services.json
```

or:

```text
app/src/main/google-services.json
```

## 2. Enable Services

Enable:

- Firestore Database
- Firebase Analytics

## 3. Apply Rules

Firestore rules:

```text
firebase/firestore.rules
```

Storage is optional. The current app does not require Firebase Storage because it does not load remote photos yet.

## 4. Seed Data

Use `firebase/sample-data.json` as the initial dataset. The easiest path is documented here:

```text
docs/SEED_FIRESTORE.md
```

The collections are:

- `toys`
- `artisans`
- `workshops`
- `educational_content`

## 5. App Keys

Add these to `local.properties`:

```properties
GEMINI_API_KEY=your_key_here
MAPS_API_KEY=your_key_here
```

Do not commit real keys.

## 6. Google Services Plugin

The plugin is declared in the root Gradle file but not applied in the app module yet, so the app can still open before `google-services.json` exists.

After adding `google-services.json`, apply this plugin in `app/build.gradle.kts`:

```kotlin
plugins {
    id("com.google.gms.google-services")
}
```
