package io.github.dkimitsa.tests;

import org.robovm.pods.firebase.core.FIRApp;
import org.robovm.pods.firebase.database.FIRDatabase;

public class DemoFirebaseDB {
    static {
        FIRApp.configure();
    }

    public static void demo() {
        FIRDatabase db = FIRDatabase.database();
        System.out.println("FirebaseDatabase initialized: " + db);
    }
}
