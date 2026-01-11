#!/usr/bin/env bash
set -Eeuo pipefail

if [[ $# -ne 1 ]]; then
  echo "Usage: convert-aar <file.aar>"
  exit 1
fi

AAR="$1"
BASENAME=$(basename "$AAR" .aar)
OUTDIR="${BASENAME}-aar"

echo "▶ Extracting AAR..."
rm -rf "$OUTDIR"
unzip -q "$AAR" -d "$OUTDIR"

cd "$OUTDIR"

if [[ ! -f classes.jar ]]; then
  echo "❌ classes.jar not found in AAR"
  exit 1
fi

echo "▶ Decompiling classes.jar with CFR..."
mkdir -p java

java -jar /opt/cfr-0.152.jar \
  classes.jar \
  --outputdir java \
  --caseinsensitivefs true \
  --comments false \
  --silent false

echo
echo "✅ Decompiled Java files:"
find java -type f | head -20
