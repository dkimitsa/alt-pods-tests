package io.github.dkimitsa.tests;

import org.robovm.pods.tenjin.TenjinSDK;

public class DemoTenjin {
    public static void demo() {
        String version = TenjinSDK.sdkVersion();
        System.out.println("Tenjin SDK version: " + version);
    }
}
