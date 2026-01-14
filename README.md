# MatatuGo

MatatuGo is an Android mobile application designed to enhance the experience of using Matatus (public transport vehicles) in Kenya. The app provides users with real-time information, route planning, and other features to make commuting easier and more efficient.

## Table of Contents
- [Project Overview](#project-overview)
- [Key Features](#key-features)
- [Architecture](#architecture)
- [Key Code and Components](#key-code-and-components)
- [Dependencies](#dependencies)
- [Coroutines Usage](#coroutines-usage)
- [Getting Started](#getting-started)
- [Build and Run](#build-and-run)

---

## Project Overview
MatatuGo is built using Kotlin and follows modern Android development practices. The project leverages Android Jetpack components, MVVM architecture, and may use coroutines for asynchronous operations.

## Key Features
- Real-time Matatu tracking and information
- Route planning and navigation
- User-friendly interface
- Integration with Android location services

## Architecture
- **Language:** Kotlin
- **Architecture Pattern:** MVVM (Model-View-ViewModel)
- **UI:** Android XML layouts
- **Asynchronous Operations:** Kotlin Coroutines (if present)
- **Dependency Management:** Gradle

## Key Code and Components
- `app/src/main/java/`: Contains all the source code for activities, view models, repositories, and utilities.
- `app/src/main/res/`: Contains resources such as layouts, drawables, and values.
- `app/src/main/AndroidManifest.xml`: App manifest file.
- `build.gradle.kts`: Project and app-level Gradle build scripts.

## Dependencies
The project uses several key dependencies (see `build.gradle.kts` for the full list):
- AndroidX libraries (AppCompat, Lifecycle, Navigation)
- Kotlin Standard Library
- Material Components
- (Potentially) Retrofit, Room, or other Jetpack libraries
- (Potentially) Coroutine libraries for async operations

## Coroutines Usage
**What is a Coroutine?**
A coroutine is a concurrency design pattern in Kotlin that allows you to write asynchronous code in a sequential manner. Coroutines help manage background tasks such as network requests or database operations without blocking the main thread.

**Are Coroutines Used in This Project?**
To confirm coroutine usage, check for imports like `kotlinx.coroutines.*` or functions such as `suspend fun`, `launch`, or `async` in the codebase. If present, coroutines are used for background operations.

## Getting Started
1. **Clone the repository:**
   ```
   git clone <repository-url>
   ```
2. **Open in Android Studio.**
3. **Sync Gradle and build the project.**
4. **Run the app on an emulator or physical device.**

## Build and Run
- Use the provided `gradlew` or `gradlew.bat` scripts to build the project:
  ```
  ./gradlew assembleDebug
  ```
- To install and run on a connected device:
  ```
  ./gradlew installDebug
  ```

## Authors
- [Dennis Mukoma](https://github.com/Murags)
- [Kristina Kemoi](https://github.com/Kr1st1naK)
- [Janny Jonyo](https://github.com/JannyFromTechSupport)
- [Cindy Ogutu](https://github.com/Bliss109) 

## Screenshots 
- Splash Screen

![WhatsApp Image 2026-01-14 at 11 08 10](https://github.com/user-attachments/assets/5c31494b-99b4-4d35-bb47-57b0c9fd87cb)

- Home Page

![WhatsApp Image 2026-01-14 at 11 10 45](https://github.com/user-attachments/assets/36e245f5-938d-45a3-9f01-b0c816301a2b)

- Picking the best Route

![WhatsApp Image 2026-01-14 at 11 10 46](https://github.com/user-attachments/assets/2af41c85-6959-4e2c-a8d3-bfaaa044a461)

- Navigating to the Desired Location

![WhatsApp Image 2026-01-14 at 11 10 47](https://github.com/user-attachments/assets/6f5665c1-6a42-4ad2-a10f-ae4b35b5f652)

- No Route Found

![WhatsApp Image 2026-01-14 at 11 10 43](https://github.com/user-attachments/assets/e287c9ae-ebfa-4598-a6d9-7df86c29375d)

- Profile Page

![WhatsApp Image 2026-01-14 at 11 10 49](https://github.com/user-attachments/assets/9796f8de-e546-419b-a34b-d028793b80d0)

- App Icon

![WhatsApp Image 2026-01-14 at 11 10 50](https://github.com/user-attachments/assets/3afd01ad-bad9-4929-9bc1-f9f70384c012)

