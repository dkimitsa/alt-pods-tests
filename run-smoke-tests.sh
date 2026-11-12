#!/usr/bin/env bash
set -uo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

LOGS_DIR="$SCRIPT_DIR/build/smoke-test-logs"
mkdir -p "$LOGS_DIR"

# List of modules to test (if no args provided)
DEFAULT_MODULES=(
    "facebook-core-basics"
    "facebook-core"
    "facebook-aem"
    "facebook-audience"
    "facebook-gaming-services-kit"
    "facebook-login-kit"
    "facebook-share-kit"
    "firebase-core"
    "firebase-analytics"
    "firebase-installation"
    "firebase-auth"
    "firebase-appcheck"
    "firebase-crashlytics"
    "firebase-database"
    "firebase-firestore"
    "firebase-messaging"
    "firebase-remoteconfig"
    "firebase-storage"
    "firebase-google-sign-in"
    "google-mobile-ads"
    "google-ump"
    "cleverads"
    "applovinsdk"
    "appsflyer"
    "singular"
    "tenjin"
    "ironsource"
    "branchmetrics"
    "onesignal"
    "revenuecat"
    "adjustsdk"
    "unityads"
    "fyber"
    "inmobi"
    "google-mobile-ads-applovin"
    "google-mobile-ads-facebook"
    "google-mobile-ads-inmobi"
    "charts"
)

MODULES=("${@:-${DEFAULT_MODULES[@]}}")

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo "================================================================="
echo "        RoboPods Headless Smoke Test Runner                     "
echo "================================================================="
echo "Date: $(date)"
echo "Modules to test: ${#MODULES[@]}"
echo "Log directory: $LOGS_DIR"
echo "-----------------------------------------------------------------"

# Step 1: Pre-flight Framework Validation using .version-metadata
echo -n -e "[${BLUE}PRE-FLIGHT${NC}] Validating .robopods-wip frameworks via .version-metadata ... "
PREFLIGHT_LOG="$LOGS_DIR/preflight-validation.log"

if ./gradlew :validateFrameworks > "$PREFLIGHT_LOG" 2>&1; then
    echo -e "${GREEN}ALL VALIDATED${NC}"
else
    echo -e "${RED}FAILED${NC}"
    echo "-----------------------------------------------------------------"
    echo "Framework validation failed! Check $PREFLIGHT_LOG for details:"
    grep -E "Framework metadata missing|Framework version mismatch" "$PREFLIGHT_LOG" || tail -n 10 "$PREFLIGHT_LOG"
    echo "-----------------------------------------------------------------"
    exit 1
fi

TOTAL=0
PASSED=0
FAILED=0
RESULTS=()

for MODULE in "${MODULES[@]}"; do
    TOTAL=$((TOTAL + 1))
    LOG_FILE="$LOGS_DIR/${MODULE}.log"
    echo -n -e "[${BLUE}RUNNING${NC}] $MODULE ... "
    START_TIME=$(date +%s)

    if ./gradlew ":$MODULE:launchIPhoneSimulator" --args="--test" > "$LOG_FILE" 2>&1; then
        END_TIME=$(date +%s)
        DURATION=$((END_TIME - START_TIME))
        if grep -q "\[SMOKE-TEST\] SUCCESS" "$LOG_FILE"; then
            echo -e "${GREEN}PASSED${NC} (${DURATION}s)"
            PASSED=$((PASSED + 1))
            RESULTS+=("PASSED|$MODULE|${DURATION}s|")
        else
            echo -e "${YELLOW}WARNING (Build OK but no SUCCESS marker)${NC} (${DURATION}s)"
            FAILED=$((FAILED + 1))
            RESULTS+=("WARNING|$MODULE|${DURATION}s|Check $LOG_FILE")
        fi
    else
        END_TIME=$(date +%s)
        DURATION=$((END_TIME - START_TIME))
        echo -e "${RED}FAILED${NC} (${DURATION}s)"
        FAILED=$((FAILED + 1))
        # Extract last 2 lines of failure from log
        ERR_SNIPPET=$(grep -E "\[VALIDATION FAILED\]" "$LOG_FILE" | head -n 1 || true)
        if [ -z "$ERR_SNIPPET" ]; then
            ERR_SNIPPET=$(tail -n 2 "$LOG_FILE" | head -n 1 | sed 's/^[ \t]*//')
        fi
        RESULTS+=("FAILED|$MODULE|${DURATION}s|$ERR_SNIPPET")
    fi
done

echo ""
echo "================================================================="
echo "                    Smoke Test Summary Report                   "
echo "================================================================="
printf "%-30s | %-10s | %-10s | %s\n" "Module" "Status" "Duration" "Notes"
echo "-----------------------------------------------------------------"

for RES in "${RESULTS[@]}"; do
    IFS="|" read -r STATUS MOD DUR NOTE <<< "$RES"
    if [ "$STATUS" == "PASSED" ]; then
        STATUS_FMT="${GREEN}PASSED${NC}  "
    elif [ "$STATUS" == "WARNING" ]; then
        STATUS_FMT="${YELLOW}WARNING${NC} "
    else
        STATUS_FMT="${RED}FAILED${NC}  "
    fi
    printf "%-30s | %-10b | %-10s | %s\n" "$MOD" "$STATUS_FMT" "$DUR" "$NOTE"
done

echo "================================================================="
echo "Total: $TOTAL | Passed: $PASSED | Failed/Warn: $FAILED"
echo "================================================================="

if [ "$FAILED" -eq 0 ]; then
    exit 0
else
    exit 1
fi
