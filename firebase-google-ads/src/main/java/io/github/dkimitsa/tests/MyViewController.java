package io.github.dkimitsa.tests;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.uikit.*;

public class MyViewController extends UIViewController {

    public MyViewController() {
        // Get the view of this view controller.
        UIView view = getView();

        // Setup background.
        view.setBackgroundColor(UIColor.white());

        // Setup banner button.
        UIButton button = new UIButton(UIButtonType.RoundedRect);
        button.setFrame(new CGRect(20, 120, 280, 40));
        button.setTitle("Banner", UIControlState.Normal);
        button.getTitleLabel().setFont(UIFont.getBoldSystemFont(22));
        button.addOnTouchUpInsideListener((control, event) -> DemoGoogleAds.demoBanner(this));
        view.addSubview(button);

        // Setup Interstitial button.
        button = new UIButton(UIButtonType.RoundedRect);
        button.setFrame(new CGRect(20, 170, 280, 40));
        button.setTitle("Interstitial", UIControlState.Normal);
        button.getTitleLabel().setFont(UIFont.getBoldSystemFont(22));
        button.addOnTouchUpInsideListener((control, event) -> DemoGoogleAds.demoInterstitial(this));
        view.addSubview(button);
    }
}
