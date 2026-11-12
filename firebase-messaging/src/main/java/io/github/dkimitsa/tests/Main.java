package io.github.dkimitsa.tests;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSData;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationDelegateAdapter;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIWindow;
import org.robovm.pods.firebase.messaging.FIRMessaging;

public class Main extends UIApplicationDelegateAdapter {
    private UIWindow window;
    private MyViewController rootViewController;

    @Override
    public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {
        // Set up the view controller.
        rootViewController = new MyViewController();

        // Create a new window at screen size.
        window = new UIWindow(UIScreen.getMainScreen().getBounds());
        // Set the view controller as the root controller for the window.
        window.setRootViewController(rootViewController);
        // Make the window visible.
        window.makeKeyAndVisible();

        return true;
    }

    @Override
    public void didRegisterForRemoteNotifications(UIApplication application, NSData deviceToken) {
        super.didRegisterForRemoteNotifications(application, deviceToken);
        FIRMessaging.messaging().setAPNSToken(deviceToken);
    }

    private static void test() {
        try (NSAutoreleasePool pool = new NSAutoreleasePool()) {
            System.out.println("[SMOKE-TEST] Running DemoFirebaseMessaging in headless test mode...");
            DemoFirebaseMessaging.demo();
            System.out.println("[SMOKE-TEST] SUCCESS: DemoFirebaseMessaging");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("[SMOKE-TEST] FAILED: DemoFirebaseMessaging");
            t.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && "--test".equals(args[0])) {
            test();
            return;
        }

        try (NSAutoreleasePool pool = new NSAutoreleasePool()) {
            UIApplication.main(args, null, Main.class);
        }
    }
}
