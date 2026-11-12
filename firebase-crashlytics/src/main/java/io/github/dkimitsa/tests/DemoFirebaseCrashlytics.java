package io.github.dkimitsa.tests;

import org.robovm.apple.foundation.NSNumber;
import org.robovm.pods.firebase.core.FIRApp;
import org.robovm.pods.firebase.crashlytics.FIRCrashlytics;

public class DemoFirebaseCrashlytics {
    static {
        FIRApp.configure();
    }

    public static void demo() {
        FIRCrashlytics crashlytics = FIRCrashlytics.crashlytics();
        crashlytics.setUserID("alt pod");
        crashlytics.setCustomValue(NSNumber.valueOf(22), "alt_key");
        crashlytics.log("test log");
        crashlytics.sendUnsentReports();
        System.out.println("FirebaseCrashlytics reports sent");
    }
}
