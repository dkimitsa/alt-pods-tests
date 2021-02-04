package io.github.dkimitsa.tests;


import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.pods.google.mobileads.GADBannerView;
import org.robovm.pods.google.mobileads.GADMobileAds;
import org.robovm.pods.google.mobileads.GADRequest;

public class DemoGoogleAds {
    // goals:
    // - check that pod is able to link/run with dependencies
    static {
        // configure once
        GADMobileAds.sharedInstance().start(status -> {
            System.out.println("GADMobileAds started with status == " + status);
        });
    }

    private static GADBannerView bannerView;

    public static void demo(UIViewController vc)
    {
        if (bannerView == null) {
            // attach banner to bottom of screen
            CGRect rect = vc.getView().getFrame();
            rect.setY(rect.getHeight() - 50);
            rect.setHeight(50);
            bannerView = new GADBannerView(rect);
            bannerView.setAdUnitID("ca-app-pub-3940256099942544/2934735716");
            bannerView.setRootViewController(vc);
            vc.getView().addSubview(bannerView);
        }
        bannerView.loadRequest(new GADRequest());
    }
}
