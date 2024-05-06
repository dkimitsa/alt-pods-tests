package io.github.dkimitsa.tests;


import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.apple.uikit.UIView;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.objc.block.VoidBlock1;
import org.robovm.pods.clearads.*;

public class DemoCleverAds {

    // pre-requirements:
    // frameworks required:
    //   - CleverAdsSolutions.xcframework
    // port of https://github.com/cleveradssolutions/CAS-iOS/blob/master/DemoApp%20Objective-C/CASSample/


    public static CASMediationManager manager;
    public static void didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {
        // Set any CAS Settings before CAS.create
        CAS.settings().setDebugMode(true);
        //[[CAS settings] updateUserWithConsent:CASConsentStatusAccepted];
        //[[CAS settings] updateCCPAWithStatus:CASCCPAStatusOptInSale];
        CAS.settings().setTaggedAudience(CASAudience.NotChildren);

        // Inform SDK of the users details
        CAS.targetingOptions().setAge(12);
        CAS.targetingOptions().setGender(CASGender.Female);

        // CAS storage last created manager
        CASManagerBuilder builder = CAS.buildManager();
        builder.withAdFlags(CASTypeFlags.with(CASTypeFlags.Banner, CASTypeFlags.Interstitial, CASTypeFlags.Rewarded));
        builder.withTestAdMode(true);
        builder.withCompletionHandler(new VoidBlock1<CASInitialConfig>() {
            @Override
            public void invoke(CASInitialConfig config) {
                if (config.getError() != null) {
                    System.out.println("[CAS Sample] Mediation manager initialization failed: " + config.getError());
                } else {
                    System.out.println("[CAS Sample] Mediation manager initialization complete.");
                }
            }
        });
        manager = builder.createMediationManager("demo");
    }


    private static CASBannerView bannerView;
    public static void demoBanner(UIViewController vc) {
        if (bannerView == null) {
            // attach banner to bottom of screen
            CGRect rect = vc.getView().getFrame();
            rect.setY(rect.getHeight() - 100);
            rect.setHeight(100);
            UIView container = new UIView(rect);
            vc.getView().addSubview(container);

            CASSize size = CASSize.getSmartBanner();
            bannerView = new CASBannerView(size, manager);
            bannerView.setTranslatesAutoresizingMaskIntoConstraints(false);
            bannerView.setRootViewController(vc);
            bannerView.setAdDelegate(createCASBannerDelegate());
            container.addSubview(bannerView);
        }

        bannerView.setHidden(false);
    }

    public static void demoInterstitial(UIViewController vc) {
        manager.presentInterstitial(vc, createCASCallback());
    }

    private static CASCallback createCASCallback() {
        return new CASCallback() {

            @Override
            public void willShown(CASStatusHandler adStatus) {
                System.out.println("willShown");
            }

            @Override
            public void didShowAdFailed(String error) {
                System.out.println("didShowAdFailed " + error);
            }

            @Override
            public void didClickedAd() {
                System.out.println("didClickedAd");
            }

            @Override
            public void didCompletedAd() {
                System.out.println("didCompletedAd");

            }

            @Override
            public void didClosedAd() {
                System.out.println("didClosedAd");
            }
        };
    }

    private static CASBannerDelegate createCASBannerDelegate() {
        return new CASBannerDelegate() {

            @Override
            public void bannerAdViewDidLoad(CASBannerView casBannerView) {
                System.out.println("bannerAdViewDidLoad");
            }

            @Override
            public void didFail(CASBannerView casBannerView, CASError casError) {
                System.out.println("didFail " + casError);
            }

            @Override
            public void willPresent(CASBannerView casBannerView, CASStatusHandler casStatusHandler) {
                System.out.println("willPresent");
            }

            @Override
            public void bannerAdViewDidRecordClick(CASBannerView casBannerView) {
                System.out.println("bannerAdViewDidRecordClick");
            }
        };
    }
}
