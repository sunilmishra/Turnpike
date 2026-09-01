# Turnpike

Turnpike is a Jetpack Compose Android app for managing driver availability and ride requests in a simple ride-hailing workflow. The app includes a home dashboard for driver status, a current ride card, and an incoming ride flow with accept/decline actions.

## Features

- Driver status management with a bottom sheet selector
- Home dashboard for current ride and incoming requests
- Incoming ride detail screen with ride actions
- Navigation built with Android Navigation 3
- Dependency injection with Hilt
- Mock repository layer for simulated ride data

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Navigation 3
- Hilt
- Kotlinx Serialization
- Gradle Kotlin DSL

## Project Structure

- `app/src/main/java/com/codewithmisu/turnpike` — app entry points and navigation
- `app/src/main/java/com/codewithmisu/turnpike/data` — mock data and repository
- `app/src/main/java/com/codewithmisu/turnpike/domain` — domain models and enums
- `app/src/main/java/com/codewithmisu/turnpike/presentation` — Compose UI and ViewModels
- `app/src/main/java/com/codewithmisu/turnpike/ui/theme` — theme resources

## Getting Started

### Prerequisites

- Android Studio (latest stable recommended)
- JDK 17+
- Android SDK configured for the project

### Run the app

1. Open the project in Android Studio.
2. Let Gradle sync complete.
3. Select an emulator or connected Android device.
4. Run the app from the `app` module.

### Build

```bash
./gradlew assembleDebug
```

## Notes

This project uses a mock data layer rather than a real backend, so the ride and driver states are simulated inside the app for demo purposes.
