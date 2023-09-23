#!/bin/sh
set -e


##
## simple snippets to run samples on simulator
##
gradle -no-daemon -info -b facebook-core-basics/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b facebook-core/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b facebook-aem/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b facebook-login-kit/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b facebook-audience/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b facebook-gaming-services-kit/build.gradle launchIPhoneSimulator
gradle -no-daemon -info -b facebook-share-kit/build.gradle launchIPhoneSimulator

