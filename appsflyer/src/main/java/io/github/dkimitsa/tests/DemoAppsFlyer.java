package io.github.dkimitsa.tests;

import org.robovm.pods.appsflyer.AppsFlyerLib;

public class DemoAppsFlyer {
    public static void demo() {
        String version = AppsFlyerLib.shared().getSdkVersion();
        System.out.println("AppsFlyer SDK version: " + version);
    }
}
