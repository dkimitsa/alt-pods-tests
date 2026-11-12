package io.github.dkimitsa.tests;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationDelegateAdapter;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIWindow;

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

    private static void test() {
        try (NSAutoreleasePool pool = new NSAutoreleasePool()) {
            System.out.println("[SMOKE-TEST] Running DemoFirebaseCrashlytics in headless test mode...");
            DemoFirebaseCrashlytics.demo();
            System.out.println("[SMOKE-TEST] SUCCESS: DemoFirebaseCrashlytics");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("[SMOKE-TEST] FAILED: DemoFirebaseCrashlytics");
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
