#!/bin/sh

set -e

if [ $1 == "sim" ]
then
  echo "Copying SIM libs"
  export SIM_OR_DEVICE=ios-arm64_x86_64-simulator
  export SIM_OR_DEVICE_i386=ios-arm64_i386_x86_64-simulator
else
  echo "Copying DEVICE libs"
  export SIM_OR_DEVICE=ios-arm64_armv7
  export SIM_OR_DEVICE_GAD=ios-arm64_armv7
fi

rm -rf core && mkdir core

#
# Core
#
rm -rf core && mkdir core
cp -R ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCore.xcframework/${SIM_OR_DEVICE}/FirebaseCore.framework core/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/GoogleUtilities.xcframework/${SIM_OR_DEVICE}/GoogleUtilities.framework core/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/PromisesObjC.xcframework/${SIM_OR_DEVICE}/PromisesObjC.framework core/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCoreInternal.xcframework/${SIM_OR_DEVICE}/FirebaseCoreInternal.framework core/
cp -R ~/Downloads/Firebase/FirebaseRemoteConfig/FirebaseRemoteConfigSwift.xcframework/${SIM_OR_DEVICE}/FirebaseRemoteConfigSwift.framework core/
cp -R ~/Downloads/Firebase/FirebaseRemoteConfig/FirebaseSharedSwift.xcframework/${SIM_OR_DEVICE}/FirebaseSharedSwift.framework core/

#
# Analytics
#
rm -rf analytics && mkdir analytics
cp -R ~/Downloads/Firebase/FirebaseAnalytics/FirebaseAnalytics.xcframework/${SIM_OR_DEVICE_i386}/FirebaseAnalytics.framework analytics/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurement.xcframework/${SIM_OR_DEVICE_i386}/GoogleAppMeasurement.framework gad/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/FirebaseInstallations.xcframework/${SIM_OR_DEVICE}/FirebaseInstallations.framework analytics/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework/${SIM_OR_DEVICE}/nanopb.framework analytics/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCoreDiagnostics.xcframework/${SIM_OR_DEVICE}/FirebaseCoreDiagnostics.framework analytics/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurement.xcframework/${SIM_OR_DEVICE_i386}/GoogleAppMeasurement.framework analytics/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/GoogleDataTransport.xcframework/${SIM_OR_DEVICE}/GoogleDataTransport.framework analytics/

#
# Auth
#
rm -rf auth && mkdir auth
cp -R ~/Downloads/Firebase/FirebaseAuth/FirebaseAuth.xcframework/${SIM_OR_DEVICE}/FirebaseAuth.framework auth/
cp -R ~/Downloads/Firebase/FirebaseAuth/GTMSessionFetcher.xcframework/${SIM_OR_DEVICE}/GTMSessionFetcher.framework auth/

#
# Firestore
#
rm -rf firestore && mkdir firestore
cp -R ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework/${SIM_OR_DEVICE}/nanopb.framework firestore/

#
# Storage
#
rm -rf storage && mkdir storage
cp -R ~/Downloads/Firebase/FirebaseStorage/FirebaseStorage.xcframework/${SIM_OR_DEVICE}/FirebaseStorage.framework storage/
cp -R ~/Downloads/Firebase/FirebaseStorage/GTMSessionFetcher.xcframework/${SIM_OR_DEVICE}/GTMSessionFetcher.framework storage/

#
# Crashlytics
#
rm -rf crashlytics && mkdir crashlytics
cp -R ~/Downloads/Firebase/FirebaseCrashlytics/FirebaseCrashlytics.xcframework/${SIM_OR_DEVICE}/FirebaseCrashlytics.framework crashlytics/

#
# Google Mobile Ads
#
rm -rf gad && mkdir gad
cp -R ~/Downloads/Firebase/Google-Mobile-Ads-SDK/GoogleMobileAds.xcframework/${SIM_OR_DEVICE}/GoogleMobileAds.framework gad/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurement.xcframework/${SIM_OR_DEVICE_i386}/GoogleAppMeasurement.framework gad/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework/${SIM_OR_DEVICE}/nanopb.framework gad/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/GoogleUtilities.xcframework/${SIM_OR_DEVICE}/GoogleUtilities.framework gad/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/PromisesObjC.xcframework/${SIM_OR_DEVICE}/PromisesObjC.framework gad/
