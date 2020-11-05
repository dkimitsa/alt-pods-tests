package io.github.dkimitsa.tests;


import org.robovm.pods.firebase.core.FIRApp;
import org.robovm.pods.firebase.storage.FIRStorage;

public class DemoStorage {

    // goals:
    // - check that pod is able to link/run with dependencies
    static {
        // configure once
        FIRApp.configure();
    }

    public static void demo() {
        FIRStorage.storage();
        System.out.println("demo");
    }
}
