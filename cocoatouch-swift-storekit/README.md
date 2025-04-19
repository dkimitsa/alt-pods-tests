# StoreKitRvm demo application 

Demonstrates usage of [StoreKitRvm](https://github.com/dkimitsa/robovm-cocoatouch-swift/storekit) bindings.

Port (mostly) of Apple's [Implementing a store in your app using the StoreKit API](https://developer.apple.com/documentation/storekit/implementing-a-store-in-your-app-using-the-storekit-api) into kotlin using [StoreKitRvm]() bindings.
Uses kotlin suspendable API to keep code flow same as Apple's sample. 

# Dispatchers.Main
Code utilizes [Dispatchers.Main](https://github.com/dkimitsa/kotlinx.coroutines.robovm) implementation to run coroutines on Main thread. Check [post](https://dkimitsa.github.io/2021/05/07/kotlinx-coroutines-dispatcher-main/) for details.   

# Testing with storekit configuration 
Apple introduced [Setting up StoreKit Testing in Xcode](https://developer.apple.com/documentation/xcode/setting-up-storekit-testing-in-xcode/).
However, RoboVM doesn't support configuring target with `.storekit` file but there is a trick how to get it deployed:
- setup xcode project with same bundle id and setup StoreKit testing;
- run it from xcode;
- after this run RoboVM app under same bundle id (it will have storekit configured as in Xcode run)

PS: there is [few insights]((https://github.com/wix/Detox/issues/2345)) on how it is being deployed to simulator but it doesn't seem to be relevant today (location is different than storeSystem.db).  