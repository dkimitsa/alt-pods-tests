#!/bin/sh

set -e

is_device_arch=0
if [ "$1" == "device" ]
then
  echo "Copying DEVICE libs"
  is_device_arch=1
else
  echo "Copying SIM libs"
fi


function copy() {
  local from=$1
  local framework=$2
  local to
  if [ $is_device_arch == "1" ]; then to="dev/${3}"; else to="sim/${3}"; fi
  local arch_dirs
  if [ $is_device_arch == "1" ]
  then
     arch_dirs=("ios-arm64" "ios-arm64_armv7")
  else
     arch_dirs=("ios-arm64_x86_64-simulator" "ios-arm64_i386_x86_64-simulator")
  fi

  for arch in "${arch_dirs[@]}"
  do
     local full_path="${from}/${arch}/${framework}"
     if [ -d "$full_path" ]
     then
       cp -R "$full_path" "$to"
       return 
     fi
  done

  >&2 echo "Failed to locate $framework in $from"
  exit -1
}

function clean_dir() {
  local target_dir
  if [ $is_device_arch == "1" ]; then target_dir="dev/${1}"; else target_dir="sim/${1}"; fi
  rm -rf "$target_dir" && mkdir -p "$target_dir"
}

#
# Core
#
clean_dir core
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCore.xcframework FirebaseCore.framework core
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCoreInternal.xcframework FirebaseCoreInternal.framework core
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleUtilities.xcframework GoogleUtilities.framework core
copy ~/Downloads/Firebase/FirebaseAnalytics/FBLPromises.xcframework FBLPromises.framework core
#copy ~/Downloads/Firebase/FirebaseRemoteConfig/FirebaseRemoteConfigSwift.xcframework FirebaseRemoteConfigSwift.framework core
#copy ~/Downloads/Firebase/FirebaseRemoteConfig/FirebaseSharedSwift.xcframework FirebaseSharedSwift.framework core

#
# Analytics
#
clean_dir analytics
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseAnalytics.xcframework FirebaseAnalytics.framework analytics
copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseInstallations.xcframework FirebaseInstallations.framework analytics
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurement.xcframework GoogleAppMeasurement.framework analytics
copy ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework nanopb.framework analytics
#copy ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCoreDiagnostics.xcframework FirebaseCoreDiagnostics.framework analytics
#copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleDataTransport.xcframework GoogleDataTransport.framework analytics

#
# Auth
#
clean_dir auth
copy ~/Downloads/Firebase/FirebaseAuth/FirebaseAuth.xcframework FirebaseAuth.framework auth
copy ~/Downloads/Firebase/FirebaseAuth/GTMSessionFetcher.xcframework GTMSessionFetcher.framework auth

#
# Firestore
#
clean_dir firestore
copy ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework nanopb.framework firestore

#
# Storage
#
clean_dir storage
copy ~/Downloads/Firebase/FirebaseStorage/FirebaseStorage.xcframework FirebaseStorage.framework storage
copy ~/Downloads/Firebase/FirebaseStorage/GTMSessionFetcher.xcframework GTMSessionFetcher.framework storage
copy ~/Downloads/Firebase/FirebaseMLModelDownloader/SwiftProtobuf.xcframework SwiftProtobuf.framework storage


#
# Crashlytics
#
clean_dir crashlytics
copy ~/Downloads/Firebase/FirebaseCrashlytics/FirebaseCrashlytics.xcframework FirebaseCrashlytics.framework crashlytics
copy ~/Downloads/Firebase/FirebaseCrashlytics/GoogleDataTransport.xcframework GoogleDataTransport.framework crashlytics

#
# Google Mobile Ads
#
clean_dir gad
copy ~/Downloads/Firebase/Google-Mobile-Ads-SDK/GoogleMobileAds.xcframework GoogleMobileAds.framework gad
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurement.xcframework GoogleAppMeasurement.framework gad
copy ~/Downloads/Firebase/FirebaseAnalytics/GoogleUtilities.xcframework GoogleUtilities.framework gad
copy ~/Downloads/Firebase/FirebaseAnalytics/FBLPromises.xcframework FBLPromises.framework gad
copy ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework nanopb.framework gad

#
# Google SignIn
#
clean_dir signin
copy ~/Downloads/Firebase/GoogleSignIn/GoogleSignIn.xcframework GoogleSignIn.framework signin
copy ~/Downloads/Firebase/GoogleSignIn/AppAuth.xcframework AppAuth.framework signin
copy ~/Downloads/Firebase/GoogleSignIn/GTMAppAuth.xcframework GTMAppAuth.framework signin
copy ~/Downloads/Firebase/GoogleSignIn/GTMSessionFetcher.xcframework GTMSessionFetcher.framework signin
