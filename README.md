# 🌾 Crop Disease Detection Android App

An intelligent Android application that helps farmers and agricultural enthusiasts identify plant diseases using image recognition and provides AI-generated solutions.

- 🔍 Built using **Java** (logic) and **Kotlin XML** (UI)
- 🧠 Powered by **Firebase ML Kit** for on-device image labeling
- 💡 Integrates **Gemini AI (Google Generative Language API)** for disease name and solution generation
- ☁️ Supports image upload and dynamic predictions
- 📱 Designed with a clean, responsive layout using XML-based UI

---

## 📸 Features

- 🌿 Select or capture crop images
- 🧠 Detect diseases using Firebase ML Kit (Image Labeling model)
- 🤖 Fetch disease name and treatment via **Gemini AI**
- ⚡ Fast, lightweight, and runs offline for image detection
- 🌐 Solution fetched via real-time Gemini AI API (requires internet)
- 🧾 Display crop disease details and treatment suggestions
- 📂 History view to store past predictions (optional)

---

## 🧱 Tech Stack

| Component        | Technology              |
|------------------|--------------------------|
| Language         | Java (backend), Kotlin XML (UI) |
| ML Detection     | Firebase ML Kit - Image Labeling |
| AI Integration   | Gemini API (Google Generative Language API) |
| UI Design        | XML Layouts |
| IDE              | Android Studio |
| API Communication| Retrofit (for Gemini API calls) |

---

## 📲 Screenshots

> ![WhatsApp Image 2025-04-18 at 11 17 27_fa1d9b50](https://github.com/user-attachments/assets/0d9d71fc-0e9b-4626-9cb6-9a307280137a)
> ![WhatsApp Image 2025-04-18 at 11 17 27_a1936d3c](https://github.com/user-attachments/assets/57b765c0-6879-4ec9-b01b-c5aef433d337)



---

## 🚀 Getting Started

### 🔧 Prerequisites

- Android Studio (Flamingo or later)
- Firebase Project
- Gemini API Key from Google AI Studio
- Internet connection for API requests

### 🔐 API Key Setup (Gemini)

1. Go to [Google AI Studio](https://aistudio.google.com/app/apikey)
2. Generate your **Gemini API Key**
3. Store it securely in `local.properties` or as a constant in a secure class.

```java
public class Constants {
    public static final String GEMINI_API_KEY = "YOUR_API_KEY_HERE";
    public static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
}
---

## Firebase ML Kit Integration

1. Add Firebase to your Android Project
Go to Firebase Console

Create a new project and add your Android app

Download google-services.json and add it to /app directory

2. Add Dependencies in build.gradle
gradle
// Firebase ML Kit Image Labeling
implementation 'com.google.mlkit:image-labeling:17.0.7'

// Firebase Core
implementation platform('com.google.firebase:firebase-bom:32.7.0')
implementation 'com.google.firebase:firebase-analytics'

// Retrofit for API calls
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

📷 Image Detection Flow
User selects image or captures photo

Firebase ML Kit performs on-device image labeling

Labels are displayed or passed to Gemini AI

Gemini AI returns:

Disease Name

Description

Solution/Treatment

📚 Directory Structure
pgsql
Copy
Edit
📂 CropDiseaseDetectionApp
├── 📁 activities/
│   └── MainActivity.java
├── 📁 fragments/
│   └── DetectFragment.java
├── 📁 utils/
│   └── RetrofitClient.java
│   └── Constants.java
├── 📁 ui/
│   └── layout XML files
├── AndroidManifest.xml
├── build.gradle
└── google-services.json
📌 Notes
Firebase ML Kit detection is offline

Gemini API requires internet access

⚙️ Permissions Required
Make sure you declare these permissions in AndroidManifest.xml:

xml

<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />
Also add this to the application tag:

xml
android:usesCleartextTraffic="true"

✅ To-Do
 Firebase ML Kit integration

 Gemini AI prompt generation and response handling

 Store past results (local DB or Firebase)

 Multilingual support

 Add loading animations

🤝 Credits
Firebase ML Kit: https://firebase.google.com/docs/ml

Gemini API: https://ai.google.dev

Icons: https://www.flaticon.com

📄 License
This project is open-source and available for educational purposes only.
Let me know if you also want me to:
- Add **GitHub badges** (build status, license, etc.)
- Write a sample Retrofit interface class
- Help generate API documentation
- Host the project on GitHub with a nice profile README

Just say the word! 🌱📱
