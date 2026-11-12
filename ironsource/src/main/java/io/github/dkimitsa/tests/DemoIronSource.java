package io.github.dkimitsa.tests;

import org.robovm.pods.ironsource.IronSource;

public class DemoIronSource {
    public static void demo() {
        String version = IronSource.sdkVersion();
        System.out.println("IronSource SDK version: " + version);
    }
}
