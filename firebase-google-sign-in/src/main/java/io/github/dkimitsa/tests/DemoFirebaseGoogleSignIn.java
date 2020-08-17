package io.github.dkimitsa.tests;


import org.robovm.pods.firebase.googlesignin.GIDSignIn;

public class DemoFirebaseGoogleSignIn {

    // pre-requirements:
    // frameworks required:
    // core:
    //   - FirebaseCore.framework
    //   - GoogleUtilities.framework
    //   - PromisesObjC.framework
    //
    // Resources:
    //   - GoogleService-Info.plist in resources folder
    //
    // goals:
    // - check that pod is able to link/run with dependencies


    static {
        // configure once
        GIDSignIn.sharedInstance().setClientID("YOUR_CLIENT_ID");
    }

    public static void demo() {
        System.out.println("demo");
    }
}
