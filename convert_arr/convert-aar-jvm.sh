#!/usr/bin/env bash
set -Eeuo pipefail

# -----------------------------
# Usage check
# -----------------------------
if [[ $# -ne 1 ]]; then
    echo "Usage: $0 <kibo_rpc_api-debug.aar>"
    exit 1
fi

AAR="$1"
BASE=$(basename "$AAR" .aar)
WORKDIR="${BASE}-jvm"

# Step 2: Decompile classes.jar
echo "▶ Decompiling classes.jar with CFR..."
DECOMPILE_DIR="$WORKDIR/java"
mkdir -p "$DECOMPILE_DIR"

API_GOV_SRC="api/src/main/java/gov"
API_ORG_SRC="api/src/main/java/org"

SRC_GETTER="api/src/main/java/jp/jaxa/iss/kibo/rpc/api/GetterNode.java"
DST_GETTER="$DECOMPILE_DIR/jp/jaxa/iss/kibo/rpc/api/GetterNode.java"

echo "▶ Working directory: $WORKDIR"
rm -rf "$WORKDIR"
mkdir -p "$WORKDIR"

# -----------------------------
# Step 1: Extract AAR
# -----------------------------
echo "▶ Extracting AAR..."
unzip -q "$AAR" -d "$WORKDIR"

CLASSES_JAR="$WORKDIR/classes.jar"
if [[ ! -f "$CLASSES_JAR" ]]; then
    echo "❌ classes.jar not found inside AAR!"
    exit 1
fi

java -jar /opt/cfr-0.152.jar "$CLASSES_JAR" \
    --outputdir "$DECOMPILE_DIR" \
    --caseinsensitivefs true \
    --comments false

API_DIR="./api"


echo "▶ Copying gov sources from api/..."
cp -r "$API_GOV_SRC" "$DECOMPILE_DIR/"

if [[ ! -f "$SRC_GETTER" ]]; then
    echo "❌ Source GetterNode not found: $SRC_GETTER"
    exit 1
fi

rm "$DECOMPILE_DIR"/jp/jaxa/iss/kibo/rpc/api/GetterNode.java
cp "$SRC_GETTER" "$DST_GETTER"

cp -r "$API_ORG_SRC" "$DECOMPILE_DIR/"

# -----------------------------
# Step 3.5: ROS Android stubs (JVM only)
# -----------------------------
echo "▶ Adding ROS Android stubs..."

# mkdir -p "$DECOMPILE_DIR/org/ros/android"

# cat > "$DECOMPILE_DIR/org/ros/android/NodeMainExecutorService.java" << 'EOF'
# package org.ros.android;

# /**
#  * JVM stub for ROS Android.
#  * This disables ROS execution but allows compilation.
#  */
# public class NodeMainExecutorService {
#     public void execute(Object node, Object config) {}
#     public void shutdown() {}
# }
# EOF

# -----------------------------
# Step 3: Add JVM-compatible Android stubs
# -----------------------------
echo "▶ Adding Android stubs..."
#ANDROID_DIR="$WORKDIR/android"
mkdir -p "$DECOMPILE_DIR/android/util"
mkdir -p "$DECOMPILE_DIR/android/os"

# Log stub
cat > "$DECOMPILE_DIR/android/util/Log.java" << 'EOF'
package android.util;
public class Log {
    public static int d(String tag, String msg) { System.out.println(tag + ": " + msg); return 0; }
    public static int i(String tag, String msg) { System.out.println(tag + ": " + msg); return 0; }
    public static int w(String tag, String msg) { System.out.println(tag + ": " + msg); return 0; }
    public static int e(String tag, String msg) { System.err.println(tag + ": " + msg); return 0; }
   public static int e(String tag, String msg, Throwable tr) {
    System.err.println(tag + ": " + msg);
    tr.printStackTrace(System.err);
    return 0;
}
    public static int v(String tag, String msg) { System.err.println(tag + ": " + msg); return 0; }
}
EOF

# Handler & Looper stubs
cat > "$DECOMPILE_DIR/android/os/Handler.java" << 'EOF'
package android.os;
public class Handler {
    public void post(Runnable r) { new Thread(r).start(); }
}
EOF

cat > "$DECOMPILE_DIR/android/os/Looper.java" << 'EOF'
package android.os;
public class Looper {
    public static Looper getMainLooper() { return new Looper(); }
}
EOF

# -----------------------------
# Step 4: Compile Java code for JVM
# -----------------------------
echo "▶ Compiling Java code for JVM..."
OUTPUT_DIR="$WORKDIR/out"
mkdir -p "$OUTPUT_DIR"

# Make sure libs folder exists
LIBS_DIR="./libs"
if [[ ! -d "$LIBS_DIR" ]]; then
    echo "❌ libs folder not found! Put all required JARs in $LIBS_DIR"
    exit 1
fi

# Find all .java files
find "$DECOMPILE_DIR" -name "*.java" > sources.txt

# Compile with all jars in libs
javac -cp "$LIBS_DIR/*" -d "$OUTPUT_DIR" @sources.txt
rm sources.txt

echo "✅ JVM-ready classes are in: $OUTPUT_DIR"
echo
echo "▶ Run with all libraries:"
echo "   java -cp \"$OUTPUT_DIR:$LIBS_DIR/*\" jp.jaxa.iss.kibo.rpc.api.KiboRpcServiceTest"
