package io.github.dkimitsa.tests;

import org.robovm.pods.unityads.UnityAds;

public class DemoUnityAds {
    public static void demo() {
        String version = UnityAds.getVersion();
        System.out.println("UnityAds SDK version: " + version);
    }
}
