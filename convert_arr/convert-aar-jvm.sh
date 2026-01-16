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

PREV_API_DIR="./api_prev"

rm -rf "$DEST_API_DIR/src/main/java"

cp -rf "$PREV_API_DIR/src/main/java" "$DEST_API_DIR/src/main/java"


API_GOV_SRC="$ORG_API_DIR/src/main/java/gov"
API_ORG_SRC="$ORG_API_DIR/src/main/java/org"

API_SUB_SRC="$ORG_API_DIR/src/main/java/jp/jaxa/iss/kibo/rpc/api/sub"
DEST_SUB_SRC="$DECOMPILE_DIR/jp/jaxa/iss/kibo/rpc/api"


SRC_GETTER="$ORG_API_DIR/src/main/java/jp/jaxa/iss/kibo/rpc/api/sub/GetterNode.java"
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


# -----------------------------
# Step 3: Add JVM-compatible Android stubs
# -----------------------------
echo "▶ Adding Android stubs..."
#ANDROID_DIR="$WORKDIR/common"
mkdir -p "$DECOMPILE_DIR/common/util"
mkdir -p "$DECOMPILE_DIR/android/os"

# Log stub
cat > "$DECOMPILE_DIR/common/util/Log.java" << 'EOF'
package common.util;
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


# -----------------------------
# Step X: Replace android.util.Log with common.util.Log
# -----------------------------
echo "▶ Replacing android.util.Log with common.util.Log in all .java files..."

find "$DECOMPILE_DIR" -name "*.java" | while read -r file; do
    # 1️⃣ Replace fully-qualified usage
    sed -i 's/android\.util\.Log/common.util.Log/g' "$file"

    # 2️⃣ Replace simple Log.* usage
    sed -i 's/\bLog\./common.util.Log./g' "$file"

    # 3️⃣ Replace import if present
    sed -i 's/import android\.util\.Log;/import common.util.Log;/g' "$file"
done

cp -rf "$DECOMPILE_DIR/common" "$DEST_API_DIR/src/main/java/"
# -----------------------------
# Step X: Replace Android Log import with Apache Commons Logging
# -----------------------------
# echo "▶ Replacing 'import android.util.Log;' with Apache Commons Logging imports..."

# find "$DECOMPILE_DIR" -name "*.java" | while read -r file; do
#     # Remove the Android import and insert Apache Commons Logging imports
#     sed -i '/import android\.util\.Log;/c\
# import org.apache.commons.logging.Log;\
# import org.apache.commons.logging.LogFactory;
# ' "$file"
# done

#exit 0

#cp -rf "$DEST_API_DIR" "$ORG_API_DIR"


#echo "▶ Copying gov sources from api/..."

#cp -r "$API_GOV_SRC" "$DECOMPILE_DIR/"

# echo "$SRC_GETTER"

# if [[ ! -f "$SRC_GETTER" ]]; then
#     echo "❌ Source GetterNode not found: $SRC_GETTER"
#     exit 1
# fi

# rm "$DECOMPILE_DIR"/jp/jaxa/iss/kibo/rpc/api/GetterNode.java
# cp "$SRC_GETTER" "$DST_GETTER"

# cp -r "$API_ORG_SRC" "$DECOMPILE_DIR/"

# cp -rf "$API_SUB_SRC" "$DEST_SUB_SRC/"


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


# =====================================================
# Patch Android dependencies for JVM simulation
# =====================================================
echo "▶ Patching KiboRpcApi.java and KiboRpcService.java..."

# -----------------------------
# Patch KiboRpcApi.java
# -----------------------------
KIBO_API_FILE=$(find "$DECOMPILE_DIR" -name "KiboRpcApi.java" | head -n 1)

if [[ -z "$KIBO_API_FILE" ]]; then
    echo "❌ KiboRpcApi.java not found!"
    exit 1
fi

# Remove Android imports
sed -i 's/import android\.app\.Activity;//g' "$KIBO_API_FILE"
sed -i 's/import gov\.nasa\.arc\.astrobee\.android\.gs\.StartGuestScienceService;//g' "$KIBO_API_FILE"

sed -i 's|import jp.jaxa.iss.kibo.rpc.api.GetterNode;|import jp.jaxa.iss.kibo.rpc.api.sub.GetterNode;|' "$KIBO_API_FILE"
sed -i 's|import jp.jaxa.iss.kibo.rpc.api.SetterNode;|import jp.jaxa.iss.kibo.rpc.api.sub.SetterNode;|' "$KIBO_API_FILE"
sed -i 's|import gov.nasa.arc.astrobee.android.gs.MessageType;|import gov.nasa.arc.astrobee.ros.internal.util.MessageType;|' "$KIBO_API_FILE"


# Remove "extends Activity"
sed -i 's/extends[[:space:]]\+Activity//g' "$KIBO_API_FILE"

# Replace StartGuestScienceService → KiboRpcService
sed -i 's/StartGuestScienceService/KiboRpcService/g' "$KIBO_API_FILE"

echo "✅ Patched KiboRpcApi.java"

# -----------------------------
# Patch KiboRpcService.java
# -----------------------------
KIBO_SERVICE_FILE=$(find "$DECOMPILE_DIR" -name "KiboRpcService.java" | head -n 1)

if [[ -z "$KIBO_SERVICE_FILE" ]]; then
    echo "❌ KiboRpcService.java not found!"
    exit 1
fi

sed -i 's|import jp.jaxa.iss.kibo.rpc.api.GetterNode;|import jp.jaxa.iss.kibo.rpc.api.sub.GetterNode;|' "$KIBO_SERVICE_FILE"
sed -i 's|import gov.nasa.arc.astrobee.android.gs.MessageType;|import gov.nasa.arc.astrobee.ros.internal.util.MessageType;|' "$KIBO_SERVICE_FILE"
# Replace StartGuestScienceService import
sed -i \
    's/import gov\.nasa\.arc\.astrobee\.android\.gs\.StartGuestScienceService;/import jp.jaxa.iss.kibo.rpc.api.sub.StartGuestScienceService;/g' \
    "$KIBO_SERVICE_FILE"

echo "✅ Patched KiboRpcService.java"

# -----------------------------
# Properly replace OpenCVLoader block in onGuestScienceStart()
# -----------------------------
echo "▶ Replacing OpenCVLoader block in KiboRpcService..."

KIBO_RPC_FILE=$(find "$DECOMPILE_DIR" -name "KiboRpcService.java" | head -n 1)

if [[ -z "$KIBO_RPC_FILE" ]]; then
    echo "⚠️ KiboRpcService.java not found"
else
    echo "▶ Patching: $KIBO_RPC_FILE"

    # 1) Remove Android OpenCV import
    sed -i '/org\.opencv\.android\.OpenCVLoader/d' "$KIBO_RPC_FILE"

    # 2) Replace the FULL if/else OpenCVLoader block
    sed -i -E '
        /if[[:space:]]*\(!OpenCVLoader\.initDebug\(\)\)[[:space:]]*\{/{
            :a
            N
            /}[[:space:]]*else[[:space:]]*\{/!ba
            N
            :b
            N
            /}/!bb
            c\
        try {\
            //System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);\
            System.load("/usr/lib/jni/libopencv_java420.so");\
            Log.d("OpenCv", "OpenCV loaded (JVM)");\
        } catch (UnsatisfiedLinkError e) {\
            Log.e("OpenCv", "Failed to load OpenCV native library", e);\
            throw e;\
        }
        }
    ' "$KIBO_RPC_FILE"
fi


# replace KiboRpcApi.java

echo "✅ Remove KiboRpcService.java from api"
rm -f "$DEST_API_DIR/src/main/java/jp.jaxa.iss.kibo.rpc.api/KiboRpcApi.java"
echo "✅ Add new KiboRpcService.java to the api"
cp -f "$DECOMPILE_DIR/jp/jaxa/iss/kibo/rpc/api/KiboRpcApi.java" "$DEST_API_DIR/src/main/java/jp/jaxa/iss/kibo/rpc/api/"
cp -f "$DECOMPILE_DIR/jp/jaxa/iss/kibo/rpc/api/KiboRpcService.java" "$DEST_API_DIR/src/main/java/jp/jaxa/iss/kibo/rpc/api/"




exit 0




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









exit 0








# Handler & Looper stubs
cat > "$DECOMPILE_DIR/android/os/Handler.java" << 'EOF'
package android.os;
public class Handler {
    public void post(Runnable r) { new Thread(r).start(); }
}
EOF


mkdir -p "$DECOMPILE_DIR/android/os"
cat > "$DECOMPILE_DIR/android/os/Looper.java" << 'EOF'
package android.os;
public class Looper {
    public static Looper getMainLooper() { return new Looper(); }
}
EOF

# -----------------------------
# Properly replace OpenCVLoader block in onGuestScienceStart()
# -----------------------------
echo "▶ Replacing OpenCVLoader block in KiboRpcService..."

KIBO_RPC_FILE=$(find "$DECOMPILE_DIR" -name "KiboRpcService.java" | head -n 1)

if [[ -z "$KIBO_RPC_FILE" ]]; then
    echo "⚠️ KiboRpcService.java not found"
else
    echo "▶ Patching: $KIBO_RPC_FILE"

    # 1) Remove Android OpenCV import
    sed -i '/org\.opencv\.android\.OpenCVLoader/d' "$KIBO_RPC_FILE"

    # 2) Replace the FULL if/else OpenCVLoader block
    sed -i -E '
        /if[[:space:]]*\(!OpenCVLoader\.initDebug\(\)\)[[:space:]]*\{/{
            :a
            N
            /}[[:space:]]*else[[:space:]]*\{/!ba
            N
            :b
            N
            /}/!bb
            c\
        try {\
            //System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);\
            System.load("/usr/lib/jni/libopencv_java420.so");\
            Log.d("OpenCv", "OpenCV loaded (JVM)");\
        } catch (UnsatisfiedLinkError e) {\
            Log.e("OpenCv", "Failed to load OpenCV native library", e);\
            throw e;\
        }
        }
    ' "$KIBO_RPC_FILE"
fi


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

