package io.github.dkimitsa.tests;

import org.robovm.pods.fyber.core.IASDKCore;

public class DemoFyber {
    public static void demo() {
        String version = IASDKCore.sharedInstance().version();
        System.out.println("Fyber Core SDK version: " + version);
    }
}
