package io.github.dkimitsa.tests;


import org.robovm.apple.uikit.UIViewController;
import org.robovm.pods.firebase.ump.UMPConsentForm;

public class DemoGoogleUmp {

    // goals:
    // - check that pod is able to link/run with dependencies
    static {
        // configure once
    }

    public static void demo(UIViewController vc) {
        System.out.println("demo");
        UMPConsentForm.loadAndPresentIfRequired(vc, err -> {
            System.out.print("Status  " + err);
        });
    }
}
