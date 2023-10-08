package io.github.dkimitsa.tests;


import org.robovm.pods.firebase.appcheck.FIRAppCheck;
import org.robovm.pods.firebase.core.FIRApp;

public class DemoAppCheck {

    // goals:
    // - check that pod is able to link/run with dependencies
    static {
        // configure once
        FIRApp.configure();
    }

    public static void demo() {
        System.out.println("demo");
        FIRAppCheck.appCheck();
    }
}
