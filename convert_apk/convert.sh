#!/usr/bin/env bash
set -e

APK="$1"
APP_NAME=$(basename "$APK" .apk)

WORKDIR="/tmp/apk2jar_$APP_NAME"
JAVA_OUT="$WORKDIR/java"
STUBS="$(pwd)/android-stubs"
JAR_OUT="$APP_NAME.jar"

echo "==> Converting $APK → $JAR_OUT"

rm -rf "$WORKDIR"
mkdir -p "$WORKDIR"

# -------------------------------
# Step 1: Decompile APK → Java
# -------------------------------
echo "==> Decompiling APK with jadx"
jadx -d "$JAVA_OUT" "$APK"

# -------------------------------
# Step 2: Remove Android imports
# -------------------------------
echo "==> Cleaning Android imports"

find "$JAVA_OUT" -name "*.java" -type f | while read -r file; do
  sed -i.bak \
    -e '/import android\./d' \
    -e '/import androidx\./d' \
    -e '/@Override/d' \
    "$file"
done

# -------------------------------
# Step 3: Compile with Android stubs
# -------------------------------
echo "==> Compiling Java sources"

BUILD="$WORKDIR/build"
mkdir -p "$BUILD"

javac \
  -source 8 -target 8 \
  -d "$BUILD" \
  $(find "$JAVA_OUT" "$STUBS" -name "*.java")

# -------------------------------
# Step 4: Build JAR
# -------------------------------
echo "==> Creating JAR"
jar cf "$JAR_OUT" -C "$BUILD" .

echo "✅ Done: $JAR_OUT"
