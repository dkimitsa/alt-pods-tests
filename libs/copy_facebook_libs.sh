#!/bin/sh

set -e


function copy() {
  local from=$1
  local to=$2
  mkdir -p "facebook/$to/"
  cp -R "$from" "facebook/$to/"
}


# cleanup 
rm -rf "facebook" && mkdir -p "facebook"


#
# Core basics
#
copy ~/Downloads/Facebook/FBSDKCoreKit_Basics.xcframework corebasics

#
# Core
#
copy ~/Downloads/Facebook/FBSDKCoreKit.xcframework core
copy ~/Downloads/Facebook/FBAEMKit.xcframework core

#
# Aem
#
copy ~/Downloads/Facebook/FBAEMKit.xcframework aem

#
# Login
#
copy ~/Downloads/Facebook/FBSDKLoginKit.xcframework login

#
# Audience
#
copy ~/Downloads/FBAudienceNetwork/Static/FBAudienceNetwork.xcframework audience

#
# Gaming Services Kit
#
copy ~/Downloads/Facebook/FBSDKGamingServicesKit.xcframework gamingservices

#
# Share kit
#
copy ~/Downloads/Facebook/FBSDKShareKit.xcframework share