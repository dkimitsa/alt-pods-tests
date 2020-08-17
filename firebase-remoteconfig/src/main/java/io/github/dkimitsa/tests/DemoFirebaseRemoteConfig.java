package io.github.dkimitsa.tests;


import org.robovm.apple.foundation.NSError;

import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.firebase.core.FIRApp;
import org.robovm.pods.firebase.remoteconfig.FIRRemoteConfig;
import org.robovm.pods.firebase.remoteconfig.FIRRemoteConfigFetchAndActivateStatus;
import org.robovm.pods.firebase.remoteconfig.FIRRemoteConfigFetchStatus;
import org.robovm.pods.firebase.remoteconfig.FIRRemoteConfigSettings;

public class DemoFirebaseRemoteConfig {

    // pre-requirements:
    // frameworks required:
    // core:
    //   - FirebaseCore.framework
    //   - GoogleUtilities.framework
    //   - PromisesObjC.framework
    // remoteconfig:
    //   - FirebaseRemoteConfig.framework
    //   - FirebaseABTesting.framework
    //
    // Resources:
    //   - GoogleService-Info.plist in resources folder
    //
    // goals:
    // - check that pod is able to link/run with dependencies


    static FIRRemoteConfig config;
    static {
        // configure once
        FIRApp.configure();
        config = FIRRemoteConfig.remoteConfig();
        test();
    }

    private static void test() {
        Object o = new Object() {
            @Override
            protected void finalize() throws Throwable {
                System.out.println("Oops");
                super.finalize();
            }
        };
        FIRRemoteConfigSettings settings = new FIRRemoteConfigSettings();
        settings.setMinimumFetchInterval(0);
        config.setConfigSettings(settings);
        config.fetchAndActivate(new VoidBlock2<FIRRemoteConfigFetchAndActivateStatus, NSError>() {
            @Override
            public void invoke(FIRRemoteConfigFetchAndActivateStatus s, NSError nsError) {
                System.out.println("Hello! " + o + s + nsError);

            }
        });
    }

    public static void demo() {
        System.out.println("demo");
    }
}
