#!/bin/sh

##
## simple snippets to run samples on simulator
##

gradle -no-daemon -info -b firebase-core/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b firebase-analytics/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b firebase-auth/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b firebase-crashlytics/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b firebase-database/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b firebase-dylinks/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b firebase-firestore/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b firebase-google-ads/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b firebase-google-sign-in/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b firebase-messaging/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b firebase-remoteconfig/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b firebase-storage/build.gradle launchIPhoneSimulator






