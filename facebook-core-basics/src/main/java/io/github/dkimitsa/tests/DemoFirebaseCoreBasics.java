package io.github.dkimitsa.tests;


import org.robovm.pods.facebook.corebasics.FBSDKBase64;

public class DemoFirebaseCoreBasics {

    // pre-requirements:
    // frameworks required:
    //   - FBSDKCoreKit_Basics.framework

    // goals:
    // - check that pod is able to link/run with dependencies

    public static void demo() {
        new FBSDKBase64();
        System.out.println("demo");
    }
}
