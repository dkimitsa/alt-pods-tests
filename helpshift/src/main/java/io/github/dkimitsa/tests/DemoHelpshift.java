package io.github.dkimitsa.tests;

import org.robovm.pods.helpshift.Helpshift;

public class DemoHelpshift {
    public static void demo() {
        Helpshift instance = Helpshift.sharedInstance();
        System.out.println("Helpshift instance: " + instance);
        System.out.println("Helpshift loadingConfig key: " + Helpshift.LoadingConfigKeys.loadingConfig());
    }
}
