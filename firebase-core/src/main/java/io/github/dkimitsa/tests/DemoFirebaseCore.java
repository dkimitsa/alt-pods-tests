package io.github.dkimitsa.tests;

import org.robovm.pods.firebase.core.FIRApp;

public class DemoFirebaseCore {
    public static void demo() {
        FIRApp.configure();
        System.out.println("Firebase app configured: " + FIRApp.defaultApp().getName());
    }
}
