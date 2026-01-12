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

ORG_API_DIR="./org_api"
DEST_API_DIR="./api"

cp -rf "$DEST_API_DIR" "$ORG_API_DIR"


API_GOV_SRC="$ORG_API_DIR/src/main/java/gov"
API_ORG_SRC="$ORG_API_DIR/src/main/java/org"

SRC_GETTER="$ORG_API_DIR/src/main/java/jp/jaxa/iss/kibo/rpc/api/GetterNode.java"
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




echo "▶ Copying gov sources from api/..."
cp -r "$API_GOV_SRC" "$DECOMPILE_DIR/"

if [[ ! -f "$SRC_GETTER" ]]; then
    echo "❌ Source GetterNode not found: $SRC_GETTER"
    exit 1
fi

rm "$DECOMPILE_DIR"/jp/jaxa/iss/kibo/rpc/api/GetterNode.java
cp "$SRC_GETTER" "$DST_GETTER"

cp -r "$API_ORG_SRC" "$DECOMPILE_DIR/"

# Example: fix Object return artifacts (manual review still recommended)
sed -i 's/Object var[0-9_]\+/Result var/g' $(grep -rl "return var" "$DECOMPILE_DIR")

# -----------------------------
# Step X: Fix CFR broken return variables (varX_Y)
# -----------------------------
echo "▶ Fixing CFR synthetic return variables..."

find "$DECOMPILE_DIR" -name "*.java" | while read -r file; do
    # Replace: return var7_10;
    sed -i -E '
        s/^[[:space:]]*return[[:space:]]+var[0-9]+_[0-9]+[[:space:]]*;/        return null;/g
    ' "$file"
done

# -----------------------------
# Fix invalid Publisher.publish((Object)msg)
# -----------------------------
echo "▶ Fixing invalid ROS publish casts..."

find "$DECOMPILE_DIR" -name "*.java" | while read -r file; do
    sed -i -E '
        s/\.publish\(\(Object\)[[:space:]]*([a-zA-Z0-9_]+)\)/.publish(\1)/g
    ' "$file"
done


# -----------------------------
# Ensure GetterNode has isNodeStarted()
# -----------------------------
echo "▶ Ensuring GetterNode.isNodeStarted() exists..."

GETTER_NODE_FILE=$(find "$DECOMPILE_DIR" -path "*jp/jaxa/iss/kibo/rpc/api/GetterNode.java" | head -n 1)

if [[ -n "$GETTER_NODE_FILE" ]]; then
    if ! grep -q "isNodeStarted" "$GETTER_NODE_FILE"; then
        echo "▶ Injecting isNodeStarted() into GetterNode"

        # Insert method before last closing brace
        sed -i '$i\
\
    public boolean isNodeStarted() {\
        return this.m_started;\
    }\
' "$GETTER_NODE_FILE"
    fi
else
    echo "⚠️ GetterNode.java not found, skipping isNodeStarted injection"
fi



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

#ls -la "$ORG_API_DIR"/src
rm -rf "$DEST_API_DIR"/src/main/java
echo "▶ Coping java folder to api..."
cp -rf "$DECOMPILE_DIR" "$DEST_API_DIR"/src/main/
echo "▶ Removing org_api folder..."
rm -rf "$ORG_API_DIR"

exit 0

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
javac -cp "$LIBS_DIR/*" -source 1.8 -target 1.8 -d "$OUTPUT_DIR" @sources.txt
rm sources.txt

echo "✅ JVM-ready classes are in: $OUTPUT_DIR"
echo
echo "▶ Run with all libraries:"
echo "   java -cp \"$OUTPUT_DIR:$LIBS_DIR/*\" jp.jaxa.iss.kibo.rpc.api.KiboRpcServiceTest"

cd kibo_rpc_api-debug-5th-jvm

jar cf kibo-rpc-api-jvm.jar -C out . && echo "✅ JAR created, listing first 20 entries:" && jar tf kibo-rpc-api-jvm.jar | head -40

