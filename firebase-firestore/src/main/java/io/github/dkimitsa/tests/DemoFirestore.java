package io.github.dkimitsa.tests;


import org.robovm.pods.firebase.core.FIRApp;
import org.robovm.pods.firebase.firestore.FIRFirestore;

public class DemoFirestore {

    // goals:
    // - check that pod is able to link/run with dependencies
    static {
        // configure once
        FIRApp.configure();
    }

    public static void demo() {
        FIRFirestore defaultFirestore = FIRFirestore.firestore();
        System.out.println("demo");
    }
}
