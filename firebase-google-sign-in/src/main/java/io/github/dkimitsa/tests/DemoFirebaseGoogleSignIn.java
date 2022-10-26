package io.github.dkimitsa.tests;


import org.robovm.apple.uikit.UIViewController;
import org.robovm.pods.firebase.googlesignin.GIDConfiguration;
import org.robovm.pods.firebase.googlesignin.GIDSignIn;

public class DemoFirebaseGoogleSignIn {

    // pre-requirements:
    // frameworks required:
    // core:
    //   - FirebaseCore.framework
    //   - GoogleUtilities.framework
    //   - PromisesObjC.framework
    //
    // Resources:
    //   - GoogleService-Info.plist in resources folder
    //
    // goals:
    // - check that pod is able to link/run with dependencies



    public static void demo(UIViewController vc) {
        GIDSignIn.sharedInstance().signIn(new GIDConfiguration("YOUR_CLIENT_ID"), vc, (gidGoogleUser, nsError) -> {
            if (gidGoogleUser != null) System.out.println(gidGoogleUser.getUserID());
            if (nsError != null) System.out.println(nsError);
        });
    }
}
