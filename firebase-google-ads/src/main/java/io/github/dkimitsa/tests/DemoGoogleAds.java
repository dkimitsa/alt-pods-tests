package io.github.dkimitsa.tests;


import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.google.mobileads.*;

import static org.robovm.pods.google.mobileads.GADRequest.GADSimulatorID;

public class DemoGoogleAds {
    // goals:
    // - check that pod is able to link/run with dependencies
    static {
        // configure once
        GADMobileAds.sharedInstance().getRequestConfiguration().setTestDeviceIdentifiers(new NSArray<>(GADSimulatorID()));
        GADMobileAds.sharedInstance().start(status -> {
            System.out.println("GADMobileAds started with status == " + status);
        });
    }

    private static GADBannerView bannerView;

    public static void demoBanner(UIViewController vc)
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

    public static void demoInterstitial(UIViewController vc)
    {
        // testing interstitial ads
        String unitId = "ca-app-pub-3940256099942544/4411468910";
        GADRequest request = new GADRequest();
        GADInterstitialAd.load(unitId, request, new VoidBlock2<GADInterstitialAd, NSError>() {
            @Override
            public void invoke(GADInterstitialAd ad, NSError error) {
                if (error != null) {
                    System.out.println("Failed to load ad due  " + error);
                } else {
                    ad.setFullScreenContentDelegate(new GADFullScreenContentDelegateAdapter(){
                        @Override
                        public void didFailToPresentFullScreenContent(GADFullScreenPresentingAd ad, NSError error) {
                            System.out.println("didFailToPresentFullScreenContent  " + error);
                        }
                    });
                    ad.presentFromRootViewController(vc);
                }
            }
        });
    }
}
