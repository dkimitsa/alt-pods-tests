package io.github.dkimitsa.tests;

import org.robovm.apple.uikit.UIViewController;
import org.robovm.pods.firebase.googlesignin.GIDSignIn;

public class DemoFirebaseGoogleSignIn {
    public static void demo() {
        System.out.println("GoogleSignIn currentUser: " + GIDSignIn.sharedInstance().getCurrentUser());
        System.out.println("GoogleSignIn hasPreviousSignIn: " + GIDSignIn.sharedInstance().hasPreviousSignIn());
    }

    public static void demo(UIViewController vc) {
        demo();
    }
}
