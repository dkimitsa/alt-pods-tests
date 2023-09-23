package io.github.dkimitsa.tests;


import org.robovm.apple.coregraphics.CGRect;
import org.robovm.pods.facebook.share.FBSDKShareButton;

public class DemoFacebookShare {

    // pre-requirements:
    // frameworks required:
    //   - FBSDKLoginKit.xcframework


    public static void demo() {
        // TODO: do something useful
        System.out.println("demo");
        new FBSDKShareButton(CGRect.Zero());
    }
}
