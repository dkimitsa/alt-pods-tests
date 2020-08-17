package io.github.dkimitsa.tests;


import org.robovm.pods.firebase.core.FIRApp;

public class DemoFirebaseDB {

    // pre-requirements:
    // frameworks required:
    // core:
    //   - FirebaseCore.framework
    //   - GoogleUtilities.framework
    //   - PromisesObjC.framework

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
