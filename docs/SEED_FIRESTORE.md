# Seed Firestore Sample Data

This project includes sample data at:

```text
firebase/sample-data.json
```

The fastest way to add it to Firebase is with the included Node script.

## 1. Create A Service Account Key

In Firebase Console:

1. Open Project Settings.
2. Go to Service accounts.
3. Click Generate new private key.
4. Save the downloaded JSON somewhere private, outside source control.

Example location:

```text
C:\Users\ankit\Downloads\channapatna-service-account.json
```

## 2. Install Script Dependencies

From the project folder:

```powershell
npm install
```

## 3. Run The Seeder

In PowerShell:

```powershell
$env:GOOGLE_APPLICATION_CREDENTIALS="C:\Users\ankit\Downloads\channapatna-service-account.json"
npm run seed:firestore
```

The script writes these collections:

- `toys`
- `artisans`
- `workshops`
- `educational_content`

It uses stable document IDs like `123456`, `ART001`, `WS001`, and `EDU001`, so you can run it again safely. Existing documents are updated with merge behavior.

## 4. Test In The App

After seeding, verify one of these Toy IDs:

- `123456`
- `234567`
- `345678`

The app should load those records from Firestore.
