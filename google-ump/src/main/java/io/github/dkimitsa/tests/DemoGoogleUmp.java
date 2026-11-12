package io.github.dkimitsa.tests;

import org.robovm.apple.uikit.UIViewController;
import org.robovm.pods.firebase.ump.UMPConsentInformation;

public class DemoGoogleUmp {
    public static void demo() {
        System.out.println("UMPConsentInformation consentStatus: " + UMPConsentInformation.getSharedInstance().getConsentStatus());
    }

    public static void demo(UIViewController vc) {
        demo();
    }
}
