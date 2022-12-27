#!/bin/sh

set -e


function copy() {
  local from=$1
  local to=$2
  cp -R "$from" "$to"
}

function clean_dir() {
  rm -rf "$1" && mkdir -p "$1"
}

#
# Core
#
clean_dir core
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCore.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCoreInternal.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleUtilities.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/FBLPromises.xcframework core
#copy ~/Downloads/Firebase/FirebaseRemoteConfig/FirebaseRemoteConfigSwift.xcframework FirebaseRemoteConfigSwift.framework core
#copy ~/Downloads/Firebase/FirebaseRemoteConfig/FirebaseSharedSwift.xcframework FirebaseSharedSwift.framework core

#
# Analytics
#
clean_dir analytics
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseAnalytics.xcframework analytics
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseInstallations.xcframework analytics
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurement.xcframework analytics
copy ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework analytics
#copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCoreDiagnostics.xcframework FirebaseCoreDiagnostics.framework analytics
#copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleDataTransport.xcframework GoogleDataTransport.framework analytics

#
# Auth
#
clean_dir auth
copy ~/Downloads/Firebase/FirebaseAuth/FirebaseAuth.xcframework auth
copy ~/Downloads/Firebase/FirebaseAuth/GTMSessionFetcher.xcframework auth

#
# Firestore
#
clean_dir firestore
copy ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework firestore

#
# Storage
#
clean_dir storage
copy ~/Downloads/Firebase/FirebaseStorage/FirebaseStorage.xcframework storage
copy ~/Downloads/Firebase/FirebaseStorage/GTMSessionFetcher.xcframework storage
copy ~/Downloads/Firebase/FirebaseMLModelDownloader/SwiftProtobuf.xcframework storage


#
# Crashlytics
#
clean_dir crashlytics
copy ~/Downloads/Firebase/FirebaseCrashlytics/FirebaseCrashlytics.xcframework crashlytics
copy ~/Downloads/Firebase/FirebaseCrashlytics/GoogleDataTransport.xcframework crashlytics

#
# Google Mobile Ads
#
clean_dir gad
copy ~/Downloads/Firebase/Google-Mobile-Ads-SDK/GoogleMobileAds.xcframework gad
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurement.xcframework gad
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleUtilities.xcframework gad
copy ~/Downloads/Firebase/FirebaseAnalytics/FBLPromises.xcframework gad
copy ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework gad

#
# Google SignIn
#
clean_dir signin
copy ~/Downloads/Firebase/GoogleSignIn/GoogleSignIn.xcframework signin
copy ~/Downloads/Firebase/GoogleSignIn/AppAuth.xcframework signin
copy ~/Downloads/Firebase/GoogleSignIn/GTMAppAuth.xcframework signin
copy ~/Downloads/Firebase/GoogleSignIn/GTMSessionFetcher.xcframework signin
