#!/bin/sh

set -e
rm -rf core && mkdir core


#
# Core
#
rm -rf core && mkdir core
cp -R ~/Downloads/Firebase/FirebaseAnalytics/FirebaseCore.xcframework/ios-arm64_i386_x86_64-simulator/FirebaseCore.framework core/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/GoogleUtilities.xcframework/ios-arm64_i386_x86_64-simulator/GoogleUtilities.framework core/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/PromisesObjC.xcframework/ios-arm64_i386_x86_64-simulator/PromisesObjC.framework core/

#
# Analytics
#
rm -rf analytics && mkdir analytics
cp -R ~/Downloads/Firebase/FirebaseAnalytics/FirebaseAnalytics.xcframework/ios-arm64_i386_x86_64-simulator analytics/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurement.xcframework/ios-arm64_i386_x86_64-simulator/GoogleAppMeasurement.framework gad/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/FirebaseInstallations.xcframework/ios-arm64_i386_x86_64-simulator/FirebaseInstallations.framework analytics/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework/ios-arm64_i386_x86_64-simulator/nanopb.framework analytics/

#
# Auth
#
rm -rf auth && mkdir auth
cp -R ~/Downloads/Firebase/FirebaseAuth/FirebaseAuth.xcframework/ios-arm64_i386_x86_64-simulator/FirebaseAuth.framework auth/
cp -R ~/Downloads/Firebase/FirebaseAuth/GTMSessionFetcher.xcframework/ios-arm64_i386_x86_64-simulator/GTMSessionFetcher.framework auth/

#
# Firestore
#
rm -rf firestore && mkdir firestore
cp -R ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework/ios-arm64_i386_x86_64-simulator/nanopb.framework firestore/

#
# Storage
#
rm -rf storage && mkdir storage
cp -R ~/Downloads/Firebase/FirebaseStorage/FirebaseStorage.xcframework/ios-arm64_i386_x86_64-simulator/FirebaseStorage.framework storage/
cp -R ~/Downloads/Firebase/FirebaseStorage/GTMSessionFetcher.xcframework/ios-arm64_i386_x86_64-simulator/GTMSessionFetcher.framework storage/

#
# Google Mobile Ads
#
rm -rf gad && mkdir gad
cp -R ~/Downloads/Firebase/Google-Mobile-Ads-SDK/GoogleMobileAds.xcframework/ios-arm64_i386_x86_64-simulator/GoogleMobileAds.framework gad/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/GoogleAppMeasurement.xcframework/ios-arm64_i386_x86_64-simulator/GoogleAppMeasurement.framework gad/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/nanopb.xcframework/ios-arm64_i386_x86_64-simulator/nanopb.framework gad/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/GoogleUtilities.xcframework/ios-arm64_i386_x86_64-simulator/GoogleUtilities.framework gad/
cp -R ~/Downloads/Firebase/FirebaseAnalytics/PromisesObjC.xcframework/ios-arm64_i386_x86_64-simulator/PromisesObjC.framework gad/
