package io.github.dkimitsa.tests;


import org.robovm.objc.block.VoidBlock1;
import org.robovm.pods.google.mobileads.GADInitializationStatus;
import org.robovm.pods.google.mobileads.GADMobileAds;

public class DemoFirebaseMobileAds {

    // pre-requirements:
    // frameworks required:
    // core:
    //   - FirebaseCore.framework
    //   - GoogleUtilities.framework
    //   - PromisesObjC.framework

    // goals:
    // - check that pod is able to link/run with dependencies
    static {
        // configure once
//        FIRApp.configure();
    }

    public static void demo() {
        GADMobileAds.sharedInstance().start(status -> System.out.println("initialized " + status));
        System.out.println("demo");
    }
}
