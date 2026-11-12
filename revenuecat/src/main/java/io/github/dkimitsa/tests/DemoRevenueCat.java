package io.github.dkimitsa.tests;

import org.robovm.pods.revenuecat.RCPurchases;

public class DemoRevenueCat {
    public static void demo() {
        String version = RCPurchases.frameworkVersion();
        System.out.println("RevenueCat SDK version: " + version);
    }
}
