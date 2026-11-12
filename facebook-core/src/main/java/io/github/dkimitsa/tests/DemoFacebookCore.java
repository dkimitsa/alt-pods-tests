package io.github.dkimitsa.tests;

import org.robovm.pods.facebook.core.FBSDKSettings;

public class DemoFacebookCore {
    public static void demo() {
        System.out.println("Facebook SDK Version: " + FBSDKSettings.sharedSettings().getSdkVersion());
    }
}
