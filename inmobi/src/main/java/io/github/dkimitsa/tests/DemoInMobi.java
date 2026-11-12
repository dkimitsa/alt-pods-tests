package io.github.dkimitsa.tests;

import org.robovm.pods.inmobi.sdk.IMSdk;

public class DemoInMobi {
    public static void demo() {
        String version = IMSdk.getVersion();
        System.out.println("InMobi SDK version: " + version);
    }
}
