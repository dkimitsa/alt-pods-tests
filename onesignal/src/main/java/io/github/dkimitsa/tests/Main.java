package io.github.dkimitsa.tests;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.*;

public class Main extends UIApplicationDelegateAdapter {
    private UIWindow window;
    private MyViewController rootViewController;

    @Override
    public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {
        rootViewController = new MyViewController();
        window = new UIWindow(UIScreen.getMainScreen().getBounds());
        window.setRootViewController(rootViewController);
        window.makeKeyAndVisible();
        return true;
    }

    private static void test() {
        try (NSAutoreleasePool pool = new NSAutoreleasePool()) {
            System.out.println("[SMOKE-TEST] Running DemoOneSignal in headless test mode...");
            DemoOneSignal.demo();
            System.out.println("[SMOKE-TEST] SUCCESS: DemoOneSignal");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("[SMOKE-TEST] FAILED: DemoOneSignal");
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
