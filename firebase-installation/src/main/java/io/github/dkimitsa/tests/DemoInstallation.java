package io.github.dkimitsa.tests;


import org.robovm.apple.foundation.NSError;
import org.robovm.apple.foundation.NSString;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.firebase.core.FIRApp;
import org.robovm.pods.firebase.installations.FIRInstallations;

public class DemoInstallation {

    // goals:
    // - check that pod is able to link/run with dependencies
    static {
        // configure once
        FIRApp.configure();
    }

    public static void demo() {
        System.out.println("demo");
        FIRInstallations.installations().installationID(new VoidBlock2<NSString, NSError>() {
            @Override
            public void invoke(NSString id, NSError err) {
                System.out.println("id " + id + " err " + err);
            }
        });
    }
}
