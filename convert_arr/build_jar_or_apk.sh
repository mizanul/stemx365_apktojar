#!/usr/bin/env bash
# ==========================================================
# Universal Gradle Build Script (JAR / APK)
# Java 8 | Ephemeral Docker | Zero Image Retention
# ==========================================================

set -Eeuo pipefail

#./update_api.sh

# ------------------------------
# Input
# ------------------------------
if [ $# -ne 1 ]; then
  echo "Usage: $0 <gradle-project-directory>"
  exit 1
fi

PROJECT_DIR="$(cd "$1" && pwd)"
IMAGE_NAME="gradle-build-java8"

# ------------------------------
# Validation
# ------------------------------
if [ ! -d "$PROJECT_DIR" ]; then
  echo "❌ ERROR: Directory not found: $PROJECT_DIR"
  exit 1
fi

if [ ! -f "$PROJECT_DIR/gradlew" ]; then
  echo "❌ ERROR: gradlew not found in $PROJECT_DIR"
  exit 1
fi

chmod +x "$PROJECT_DIR/gradlew"

# ------------------------------
# Detect project type
# ------------------------------
BUILD_TASK="jar"
PROJECT_TYPE="JAR"

if [ -f "$PROJECT_DIR/app/build.gradle" ] || \
   [ -f "$PROJECT_DIR/app/build.gradle.kts" ]; then
  BUILD_TASK="assembleDebug"
  PROJECT_TYPE="APK"
fi

echo "🔍 Detected project type: $PROJECT_TYPE"

# ------------------------------
# Build Docker image
# ------------------------------
echo "🔨 Building ephemeral Gradle image..."
docker build -f Dockerfile_gradle -t "$IMAGE_NAME" .

# ------------------------------
# Run Gradle build
# ------------------------------
echo "🚀 Running Gradle task: $BUILD_TASK"

docker run --rm \
  -v "$PROJECT_DIR:/workspace" \
  "$IMAGE_NAME" \
  bash -c "
    cd /workspace &&
    ./gradlew clean $BUILD_TASK
  "

# ------------------------------
# Cleanup image
# ------------------------------
echo "🧹 Cleaning up Docker image..."
#docker rmi "$IMAGE_NAME" >/dev/null 2>&1 || true

# ------------------------------
# Output hint
# ------------------------------
echo "✅ Build finished"

if [ "$PROJECT_TYPE" = "JAR" ]; then
  echo "➡ JAR output: build/libs/"
else
  echo "➡ APK output: app/build/outputs/apk/debug/"
fi
