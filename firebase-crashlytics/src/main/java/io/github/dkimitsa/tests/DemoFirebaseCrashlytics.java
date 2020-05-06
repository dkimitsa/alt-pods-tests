package io.github.dkimitsa.tests;


import org.robovm.apple.dispatch.DispatchQueue;
import org.robovm.apple.foundation.NSException;
import org.robovm.apple.foundation.NSNumber;
import org.robovm.pods.firebase.core.FIRApp;
import org.robovm.pods.firebase.crashlytics.FIRCrashlytics;

import java.util.concurrent.TimeUnit;

public class DemoFirebaseCrashlytics {

    // pre-requirements:
    // frameworks required:
    // core:
    //   - FirebaseCore.framework
    //   - GoogleUtilities.framework
    //   - PromisesObjC.framework
    // crashlytics:
    //   - FirebaseCrashlytics.framework
    //   - FirebaseInstallations.framework
    //   - GoogleDataTransport.framework
    //   - nanopb.framework
    //
    // Resources:
    //   - GoogleService-Info.plist in resources folder
    //
    // Run configuration:
    //   - add `-FIRDebugEnabled` to program arguments in run configuration (idea)
    //
    // goals:
    // - check that pod is able to link/run with dependencies
    // - can send crash report


    static {
        // configure once
        FIRApp.configure();
    }

    public static void demo() {
        FIRCrashlytics.crashlytics().setUserID("alt pod");
        FIRCrashlytics.crashlytics().setCustomValue(NSNumber.valueOf(22), "alt_key");
        FIRCrashlytics.crashlytics().log("test log");
        FIRCrashlytics.crashlytics().sendUnsentReports();

        // now crash after 2 seconds to allow previous reports to be delivered
        DispatchQueue.getMainQueue().after(2, TimeUnit.SECONDS,
                () -> new NSException("alt pod crash", "crash", null).raise());
    }
}
