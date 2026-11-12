package io.github.dkimitsa.tests;

import org.robovm.pods.firebase.auth.FIRAuth;
import org.robovm.pods.firebase.core.FIRApp;

public class DemoFirebaseAuth {
    static {
        FIRApp.configure();
    }

    public static void demo() {
        System.out.println("Firebase auth initialized, currentUser: " + FIRAuth.auth().getCurrentUser());
    }
}
