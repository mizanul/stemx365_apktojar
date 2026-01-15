#!/usr/bin/env bash

set -e

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 Old.java New.java"
    exit 1
fi

OLD="$1"
NEW="$2"

if [[ ! -f "$OLD" || ! -f "$NEW" ]]; then
    echo "❌ One or both Java files do not exist"
    exit 1
fi

TMP_OLD=$(mktemp)
TMP_NEW=$(mktemp)

cleanup() {
    rm -f "$TMP_OLD" "$TMP_NEW"
}
trap cleanup EXIT

extract_methods() {
    sed -E '
        s@//.*@@g
        s@/\*.*\*/@@g
    ' "$1" |
    grep -E '^[[:space:]]*(public|private)[[:space:]]+' |
    grep -E '\(.*\)' |
    grep -v ' class ' |
    sed -E '
        s/^[[:space:]]*(public|private)[[:space:]]+//
        s/[[:space:]]*throws.*//
        s/[[:space:]]*\{.*//
    ' |
    sort -u
}

extract_methods "$OLD" > "$TMP_OLD"
extract_methods "$NEW" > "$TMP_NEW"

echo
echo "=============================================================="
echo " JAVA METHOD COMPARISON (NEW METHODS MARKED)"
echo "=============================================================="
printf "%-60s | %-60s | STATUS\n" "OLD FILE" "NEW FILE"
printf "%-60s-+-%-60s-+--------\n" \
  "$(printf '%*s' 60 | tr ' ' '-')" \
  "$(printf '%*s' 60 | tr ' ' '-')"

ALL_METHODS=$(cat "$TMP_OLD" "$TMP_NEW" | sort -u)

while read -r method; do
    in_old=false
    in_new=false

    grep -Fxq "$method" "$TMP_OLD" && in_old=true
    grep -Fxq "$method" "$TMP_NEW" && in_new=true

    old_col=""
    new_col=""
    status=""

    if $in_old && $in_new; then
        old_col="$method"
        new_col="$method"
        status="UNCHANGED"
    elif $in_old && ! $in_new; then
        old_col="$method"
        status="REMOVED"
    elif ! $in_old && $in_new; then
        new_col="$method"
        status="NEW"
    fi

    printf "%-60s | %-60s | %s\n" "$old_col" "$new_col" "$status"
done <<< "$ALL_METHODS"
