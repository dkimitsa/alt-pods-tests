#!/bin/sh

set -e


function copy() {
  local from=$1
  local to=$2
  mkdir -p "firebase/$to/"
  cp -R "$from" "firebase/$to/"
}


# cleanup 
rm -rf "firebase" && mkdir -p "firebase"

#
# Core
#
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCore.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCoreInternal.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleUtilities.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/FBLPromises.xcframework core

#
# Analytics
#
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseAnalytics.xcframework analytics
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseInstallations.xcframework analytics
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurement.xcframework analytics
copy ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework analytics

#
# Auth
#
copy ~/Downloads/Firebase/FirebaseAuth/FirebaseAuth.xcframework auth
copy ~/Downloads/Firebase/FirebaseAuth/GTMSessionFetcher.xcframework auth
copy ~/Downloads/Firebase/FirebaseAuth/FirebaseAppCheckInterop.xcframework auth
copy ~/Downloads/Firebase/FirebaseAuth/RecaptchaInterop.xcframework auth

#
# Crashlytics
#
copy ~/Downloads/Firebase/FirebaseCrashlytics/FirebaseCrashlytics.xcframework crashlytics
copy ~/Downloads/Firebase/FirebaseCrashlytics/GoogleDataTransport.xcframework crashlytics
copy ~/Downloads/Firebase/FirebaseCrashlytics/FirebaseSessions.xcframework crashlytics
copy ~/Downloads/Firebase/FirebaseCrashlytics/PromisesSwift.xcframework crashlytics
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseInstallations.xcframework crashlytics
copy ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework crashlytics

#
# FirebaseDatabase
#
copy ~/Downloads/Firebase/FirebaseDatabase/FirebaseDatabase.xcframework db
copy ~/Downloads/Firebase/FirebaseDatabase/leveldb-library.xcframework db

#
# FirebaseDynamicLinks
#
copy ~/Downloads/Firebase/FirebaseDynamicLinks/FirebaseDynamicLinks.xcframework dylinks

#
# Firestore
#
copy ~/Downloads/Firebase/FirebaseFirestore/abseil.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/BoringSSL-GRPC.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/FirebaseFirestore.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/gRPC-C++.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/gRPC-Core.xcframework firestore
copy ~//Downloads/Firebase/FirebaseFirestore/leveldb-library.xcframework firestore
copy ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework firestore


#
# Google Mobile Ads
#
copy ~/Downloads/Firebase/Google-Mobile-Ads-SDK/GoogleMobileAds.xcframework gad
copy ~/Downloads/Firebase/Google-Mobile-Ads-SDK/UserMessagingPlatform.xcframework gad
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurement.xcframework gad
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleUtilities.xcframework gad
copy ~/Downloads/Firebase/FirebaseAnalytics/FBLPromises.xcframework gad
copy ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework gad

#
# Google SignIn
#
copy ~/Downloads/Firebase/GoogleSignIn/GoogleSignIn.xcframework signin
copy ~/Downloads/Firebase/GoogleSignIn/AppAuth.xcframework signin
copy ~/Downloads/Firebase/GoogleSignIn/GTMAppAuth.xcframework signin
copy ~/Downloads/Firebase/GoogleSignIn/GTMSessionFetcher.xcframework signin

#
# UserMessagingPlatform
#
copy ~/Downloads/Firebase/Google-Mobile-Ads-SDK/UserMessagingPlatform.xcframework ump

#
# FirebaseInstallations
#
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseInstallations.xcframework inst

#
# FirebaseMessaging
#
copy ~/Downloads/Firebase/FirebaseMessaging/FirebaseMessaging.xcframework messaging
copy ~/Downloads/Firebase/FirebaseMessaging/GoogleDataTransport.xcframework messaging
copy ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework messaging
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseInstallations.xcframework messaging

#
# FirebaseRemoteConfig
#
copy ~/Downloads/Firebase/FirebaseRemoteConfig/FirebaseABTesting.xcframework remoteconfig
copy ~/Downloads/Firebase/FirebaseRemoteConfig/FirebaseRemoteConfig.xcframework remoteconfig
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseInstallations.xcframework remoteconfig

#
# Storage
#
copy ~/Downloads/Firebase/FirebaseStorage/FirebaseAppCheckInterop.xcframework storage
copy ~/Downloads/Firebase/FirebaseStorage/FirebaseAuthInterop.xcframework storage
copy ~/Downloads/Firebase/FirebaseStorage/FirebaseCoreExtension.xcframework storage
copy ~/Downloads/Firebase/FirebaseStorage/FirebaseStorage.xcframework storage
copy ~/Downloads/Firebase/FirebaseStorage/GTMSessionFetcher.xcframework storage
copy ~/Downloads/Firebase/FirebaseStorage/FirebaseStorage.xcframework storage

