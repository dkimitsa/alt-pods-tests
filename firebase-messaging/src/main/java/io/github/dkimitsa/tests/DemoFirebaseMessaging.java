package io.github.dkimitsa.tests;


import org.robovm.pods.firebase.core.FIRApp;

public class DemoFirebaseMessaging {

    // pre-requirements:
    // frameworks required:
    // core:
    //   - FirebaseCore.framework
    //   - GoogleUtilities.framework
    //   - PromisesObjC.framework
    // remoteconfig:
    //   - FirebaseMessaging.framework
    //
    // Resources:
    //   - GoogleService-Info.plist in resources folder
    //
    // Info.plist:
    //   - Disable method swizzling by adding following:
    //     <key>FirebaseAppDelegateProxyEnabled</key>
    //     <false />
    // in didRegisterForRemoteNotificationsWithDeviceToken make sure to set APNStoken
    //     FIRMessaging.messaging().setAPNSToken(deviceToken);
    //
    // goals:
    // - check that pod is able to link/run with dependencies
    static {
        // configure once
        FIRApp.configure();
    }

    public static void demo() {
        System.out.println("demo");
    }
}
