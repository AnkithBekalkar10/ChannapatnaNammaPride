# Firestore Admin Setup Guide

This document explains how to configure Firebase Authentication for admin access in the Channapatna Namma Pride app.

## Current Implementation

The app uses Firebase Authentication with email/password for admin login. The authentication logic is implemented in:

- **Repository**: `FirebaseChannapatnaRepository.adminSignIn()`
- **ViewModel**: `ChannapatnaViewModel.adminSignIn()`
- **UI**: `AdminScreen` (now shows login first, then artisan management)

## Setup Instructions

### 1. Firebase Console Configuration

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Select your project (or create a new one)
3. Enable Authentication:
   - Go to Authentication → Sign-in method
   - Enable "Email/Password" provider
4. Download `google-services.json` and place it in `app/google-services.json`

### 2. Create Admin User

#### Option A: Firebase Console
1. Go to Authentication → Users
2. Click "Add user"
3. Enter admin email and password
4. Click "Add user"

#### Option B: In-App Registration
The current app doesn't have user registration, so you'll need to create the first admin user through the console.

### 3. Security Rules

Configure Firestore security rules in Firestore Console → Rules:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Public read access for all collections
    match /{document=**} {
      allow read: if true;
    }
    
    // Admin-only write access
    match /artisans/{document} {
      allow write: if request.auth != null;
    }
    
    match /toys/{document} {
      allow write: if request.auth != null;
    }
    
    match /workshops/{document} {
      allow write: if request.auth != null;
    }
    
    match /educational_content/{document} {
      allow write: if request.auth != null;
    }
  }
}
```

### 4. Admin Credentials

Use these credentials to test the admin functionality:

- **Email**: Your admin email (configured in Firebase Console)
- **Password**: Your admin password (configured in Firebase Console)

## App Flow

1. **Initial State**: Admin screen shows only login form
2. **After Login**: Screen shows artisan management form with sign out button
3. **Sign Out**: Returns to login form

## Testing

1. Build and run the app
2. Navigate to Admin tab
3. Enter admin credentials
4. Verify you can see the artisan management form
5. Test adding a new artisan
6. Test sign out functionality

## Security Notes

- The app uses Firebase Authentication tokens for verification
- Admin status is checked before allowing artisan creation
- The `isAdminSignedIn()` method checks `auth.currentUser != null`
- Firestore security rules enforce server-side validation

## Troubleshooting

### Login Issues
- Verify Firebase project configuration
- Check `google-services.json` is correctly placed
- Ensure Authentication is enabled in Firebase Console

### Permission Issues
- Verify Firestore security rules
- Check admin user exists in Authentication
- Ensure email/password are correct

### Build Issues
- Verify Firebase SDK dependencies in `build.gradle`
- Check internet connectivity for Firebase services

## Dependencies

The app uses these Firebase dependencies:
```gradle
implementation 'com.google.firebase:firebase-auth-ktx'
implementation 'com.google.firebase:firebase-firestore-ktx'
```
