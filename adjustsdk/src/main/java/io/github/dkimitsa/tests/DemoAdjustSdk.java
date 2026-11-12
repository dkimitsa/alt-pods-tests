package io.github.dkimitsa.tests;

import org.robovm.pods.adjustsdk.Adjust;

public class DemoAdjustSdk {
    public static void demo() {
        String env = Adjust.Environments.Sandbox();
        System.out.println("Adjust Sandbox env symbol: " + env);
        Adjust.sdkVersion(v -> System.out.println("Adjust SDK version callback: " + v));
    }
}
