# 🎨 Channapatna – Namma Pride

A heritage-focused Android application built to preserve, authenticate, and celebrate the traditional wooden toy craftsmanship of Channapatna, Karnataka.

The app transforms wooden toy authentication into an emotional storytelling experience by giving every GI-tagged toy a digital identity connected to the artisan who crafted it.

---

## 📱 Project Overview

**Channapatna – Namma Pride** is an Android application developed using **Kotlin**, **Jetpack Compose**, **Firebase**, and **Google Gemini AI**.

The platform helps users:

* Verify the authenticity of Channapatna wooden toys
* Learn about artisans and their craftsmanship
* Explore the cultural heritage behind the toys
* Locate workshops using maps
* Browse toy collections and educational content
* Experience AI-generated storytelling connected to each toy

This project combines technology, culture, and storytelling to support local artisans and preserve Karnataka’s traditional toy-making heritage.

---

## ✨ Features

### ✅ Toy Verification System

* 6-digit toy authentication system
* Verify genuine GI-tagged Channapatna toys
* Instant verification using Firebase Firestore

### 👨‍🎨 Artisan Profiles

* Detailed artisan information
* Experience and specialization details
* Kannada and English support
* AI-generated stories about artisans and toys

### 🗺️ Workshop Locator

* Integrated Google Maps support
* View workshop locations
* Explore artisan communities

### 🛍️ Toy Catalog

* Browse traditional toy collections
* View categories and toy details
* Interactive and colorful UI

### 📚 Educational Section

* Learn how Channapatna toys are made
* Explore heritage and crafting processes
* Multimedia educational content

### 🌐 Localization Support

* English and Kannada language support
* Dynamic language switching

### 🤖 Gemini AI Integration

* AI-generated storytelling
* Emotion-driven cultural narratives
* Personalized artisan stories

---

## 🏗️ Architecture

The project follows the **MVVM (Model-View-ViewModel)** architecture pattern.

```text
UI Layer (Jetpack Compose)
        ↓
ViewModels
        ↓
Repositories
        ↓
Firebase / Gemini AI
```

### Architecture Components

* **Presentation Layer** → Jetpack Compose UI
* **Business Logic Layer** → ViewModels + State Management
* **Data Layer** → Repository Pattern
* **Backend** → Firebase Firestore & Storage

---

## 🛠️ Tech Stack

| Category             | Technology            |
| -------------------- | --------------------- |
| Language             | Kotlin                |
| UI Toolkit           | Jetpack Compose       |
| Architecture         | MVVM                  |
| Backend              | Firebase Firestore    |
| Storage              | Firebase Storage      |
| AI Integration       | Google Gemini AI      |
| Maps                 | Google Maps SDK       |
| Dependency Injection | Hilt                  |
| Image Loading        | Coil                  |
| State Management     | StateFlow + ViewModel |
| Async Operations     | Kotlin Coroutines     |

---

## 📂 Project Structure

```text
ChannapatnaNammaPride/
│
├── app/                    # Android application source
├── docs/                   # Documentation files
├── firebase/               # Firebase configuration
├── scripts/                # Utility scripts
├── gradle/                 # Gradle wrapper
├── FIRESTORE_ADMIN_SETUP.md
├── PROJECT_NOTES.md
├── README.md
└── build.gradle.kts
```

---

## 🔥 Firebase Setup

### 1. Create Firebase Project

* Open Firebase Console
* Create a new project
* Enable Firestore Database
* Enable Firebase Storage

### 2. Add Android App

* Register your Android package name
* Download `google-services.json`
* Place it inside:

```text
app/google-services.json
```

### 3. Enable Services

* Firestore Database
* Firebase Storage
* Firebase Analytics

### 4. Configure Security Rules

Use the rules provided in:

```text
FIRESTORE_ADMIN_SETUP.md
```

---

## 🚀 Installation & Setup

### Prerequisites

* Android Studio Hedgehog or later
* Kotlin 1.9+
* Firebase Project
* Google Maps API Key
* Gemini API Key

### Clone Repository

```bash
git clone https://github.com/AnkithBekalkar10/ChannapatnaNammaPride.git
```

### Open Project

Open the project in Android Studio.

### Sync Gradle

Allow Gradle to sync all dependencies.

### Add API Keys

Configure:

* Firebase credentials
* Google Maps API Key
* Gemini API Key

### Run Application

```bash
./gradlew installDebug
```

Or run directly using Android Studio.

---

## 📸 Main Screens

### 🏠 Home Screen

* Animated logo
* Navigation cards
* Language switch support

### 🔍 Verification Screen

* Enter 6-digit toy ID
* View authentication result
* Access artisan details

### 👨‍🎨 Artisan Story Screen

* AI-generated storytelling
* Toy information
* Heritage details

### 🗺️ Workshop Map

* Interactive workshop locations
* Google Maps integration

### 📚 Education Screen

* Traditional toy-making process
* Craft heritage learning section

---

## 🎯 Project Goals

* Preserve Channapatna toy heritage
* Support local artisans digitally
* Prevent counterfeit products
* Increase cultural awareness
* Promote Karnataka’s GI-tagged crafts

---

## 🌟 Future Improvements

* QR code scanning for toy verification
* Online marketplace integration
* Artisan video interviews
* User authentication system
* Certificate generation & sharing
* AR/VR toy showcase
* Multi-platform support

---

## 🤝 Contributing

Contributions are welcome.

### Steps to Contribute

1. Fork the repository
2. Create a new branch
3. Commit your changes
4. Push to your branch
5. Create a Pull Request

---

## 📄 License

This project is developed for educational and heritage preservation purposes.

---

## 👨‍💻 Developer

Developed by **Ankith Bekalkar**

GitHub:

[AnkithBekalkar10 GitHub](https://github.com/AnkithBekalkar10?utm_source=chatgpt.com)

Repository:

[Channapatna – Namma Pride Repository](https://github.com/AnkithBekalkar10/ChannapatnaNammaPride?utm_source=chatgpt.com)

---

## 💛 About Channapatna Toys

Channapatna toys are traditional wooden toys handcrafted in Karnataka, India. Known for their vibrant colors and eco-friendly lacquer finish, these toys hold a Geographical Indication (GI) tag and represent over 200 years of craftsmanship heritage.
