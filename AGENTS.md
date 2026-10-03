# Notes
- Native Android app (no web UI). Compose builds a debug APK with Gradle 8.9 + Android SDK 34 and serves `dist/` on port 3000.
- The repo's `gradlew` is a dummy; use the `gradle` in the build image.
- Sceneform (com.gorisse 1.23.0) uses the `com.google.ar.sceneform.ux` Java-style API.
- Rebuild after code changes: `docker compose -f docker-compose.base44.yml up -d --force-recreate`.
