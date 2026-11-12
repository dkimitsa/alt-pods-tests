package io.github.dkimitsa.tests;

import org.robovm.pods.firebase.core.FIRApp;
import org.robovm.pods.firebase.messaging.FIRMessaging;

public class DemoFirebaseMessaging {
    static {
        FIRApp.configure();
    }

    public static void demo() {
        FIRMessaging messaging = FIRMessaging.messaging();
        System.out.println("FirebaseMessaging initialized: " + messaging);
    }
}
