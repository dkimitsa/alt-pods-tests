package io.github.dkimitsa.tests;

import org.robovm.pods.singular.Singular;

public class DemoSingular {
    public static void demo() {
        String version = Singular.version();
        System.out.println("Singular SDK version: " + version);
    }
}
