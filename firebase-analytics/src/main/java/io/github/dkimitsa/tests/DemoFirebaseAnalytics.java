package io.github.dkimitsa.tests;


import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSMutableDictionary;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSString;
import org.robovm.pods.firebase.analytics.FIRAnalytics;
import org.robovm.pods.firebase.analytics.FIREvents;
import org.robovm.pods.firebase.analytics.FIRParameters;
import org.robovm.pods.firebase.core.FIRApp;

public class DemoFirebaseAnalytics {

    // pre-requirements:
    // frameworks required:
    // core:
    //   - FirebaseCore.framework
    //   - GoogleUtilities.framework
    //   - PromisesObjC.framework
    // analytics:
    //   - FirebaseAnalytics.framework
    //   - GoogleAppMeasurement.framework
    //   - FirebaseInstallations.framework
    //   - nanopb.framework
    //
    // Resources:
    //   - GoogleService-Info.plist in resources folder
    //
    // Run configuration:
    //   - add `-FIRAnalyticsDebugEnabled` to program arguments in run configuration (idea)
    //
    // goals:
    // - check that pod is able to link/run with dependencies
    // - can send event in console


    static {
        // configure once
        FIRApp.configure();
    }

    public static void demo() {
        FIRAnalytics.logEvent(FIREvents.SelectContent,
                new NSDictionary<>(
                        new NSString(FIRParameters.ItemID), new NSString("id-00123"),
                        new NSString(FIRParameters.ItemName), new NSString("demo"),
                        new NSString(FIRParameters.ContentType), new NSString("image")));
    }
}
