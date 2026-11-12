package io.github.dkimitsa.tests;

import org.robovm.pods.onesignal.OneSignal;

public class DemoOneSignal {
    public static void demo() {
        double version = OneSignal.OSCoreVersionNumber();
        System.out.println("OneSignal SDK version: " + version);
    }
}
