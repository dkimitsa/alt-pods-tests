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
copy ~/Downloads/Firebase/FirebaseAnalytics/FBLPromises.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCore.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCoreInternal.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseInstallations.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurement.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurementIdentitySupport.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleUtilities.xcframework core
copy ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework core

#
# Analytics
#
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseAnalytics.xcframework analytics

#
# Auth
#
copy ~/Downloads/Firebase/FirebaseAuth/FirebaseAppCheckInterop.xcframework auth
copy ~/Downloads/Firebase/FirebaseAuth/FirebaseAuth.xcframework auth
copy ~/Downloads/Firebase/FirebaseAuth/GTMSessionFetcher.xcframework auth
copy ~/Downloads/Firebase/FirebaseAuth/RecaptchaInterop.xcframework auth

#
# AppCheck
#
copy ~/Downloads/Firebase/FirebaseAppCheck/AppCheckCore.xcframework appcheck
copy ~/Downloads/Firebase/FirebaseAppCheck/FirebaseAppCheck.xcframework appcheck
copy ~/Downloads/Firebase/FirebaseAppCheck/FirebaseAppCheckInterop.xcframework appcheck

#
# Crashlytics
#
copy ~/Downloads/Firebase/FirebaseCrashlytics/FirebaseCoreExtension.xcframework crashlytics
copy ~/Downloads/Firebase/FirebaseCrashlytics/FirebaseCrashlytics.xcframework crashlytics
copy ~/Downloads/Firebase/FirebaseCrashlytics/FirebaseSessions.xcframework crashlytics
copy ~/Downloads/Firebase/FirebaseCrashlytics/GoogleDataTransport.xcframework crashlytics
copy ~/Downloads/Firebase/FirebaseCrashlytics/Promises.xcframework crashlytics
copy ~/Downloads/Firebase/FirebaseCrashlytics/FirebaseRemoteConfigInterop.xcframework crashlytics

#
# FirebaseDatabase
#
copy ~/Downloads/Firebase/FirebaseDatabase/FirebaseAppCheckInterop.xcframework db
copy ~/Downloads/Firebase/FirebaseDatabase/FirebaseDatabase.xcframework db
copy ~/Downloads/Firebase/FirebaseDatabase/FirebaseSharedSwift.xcframework db
copy ~/Downloads/Firebase/FirebaseDatabase/leveldb.xcframework db

#
# FirebaseDynamicLinks
#
copy ~/Downloads/Firebase/FirebaseDynamicLinks/FirebaseDynamicLinks.xcframework dylinks

#
# Firestore
#
copy ~/Downloads/Firebase/FirebaseFirestore/FirebaseAppCheckInterop.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/FirebaseCoreExtension.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/FirebaseFirestore.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/FirebaseFirestoreInternal.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/FirebaseSharedSwift.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/absl.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/grpc.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/grpcpp.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/leveldb.xcframework firestore
copy ~/Downloads/Firebase/FirebaseFirestore/openssl_grpc.xcframework firestore


#
# Google Mobile Ads
#
copy ~/Downloads/Firebase/Google-Mobile-Ads-SDK/GoogleMobileAds.xcframework gad
copy ~/Downloads/Firebase/Google-Mobile-Ads-SDK/UserMessagingPlatform.xcframework gad

#
# Google SignIn
#
copy ~/Downloads/Firebase/GoogleSignIn/AppAuth.xcframework signin
copy ~/Downloads/Firebase/GoogleSignIn/AppCheckCore.xcframework signin
copy ~/Downloads/Firebase/GoogleSignIn/GTMAppAuth.xcframework signin
copy ~/Downloads/Firebase/GoogleSignIn/GTMSessionFetcher.xcframework signin
copy ~/Downloads/Firebase/FirebaseAnalytics/FBLPromises.xcframework signin
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleUtilities.xcframework signin
copy ~/Downloads/Firebase/GoogleSignIn/GoogleSignIn.xcframework signin

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

#
# FirebaseRemoteConfig
#
copy ~/Downloads/Firebase/FirebaseRemoteConfig/FirebaseABTesting.xcframework remoteconfig
copy ~/Downloads/Firebase/FirebaseRemoteConfig/FirebaseRemoteConfig.xcframework remoteconfig
copy ~/Downloads/Firebase/FirebaseRemoteConfig/FirebaseSharedSwift.xcframework remoteconfig
copy ~/Downloads/Firebase/FirebaseRemoteConfig/FirebaseRemoteConfigInterop.xcframework remoteconfig

#
# Storage
#
copy ~/Downloads/Firebase/FirebaseStorage/FirebaseAppCheckInterop.xcframework storage
copy ~/Downloads/Firebase/FirebaseStorage/FirebaseAuthInterop.xcframework storage
copy ~/Downloads/Firebase/FirebaseStorage/FirebaseCoreExtension.xcframework storage
copy ~/Downloads/Firebase/FirebaseStorage/FirebaseStorage.xcframework storage
copy ~/Downloads/Firebase/FirebaseStorage/GTMSessionFetcher.xcframework storage

