package io.github.dkimitsa.tests;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.uikit.*;

public class MyViewController extends UIViewController {
    private final UILabel label;

    public MyViewController() {
        // Get the view of this view controller.
        UIView view = getView();

        // Setup background.
        view.setBackgroundColor(UIColor.white());

        // Setup label.
        label = new UILabel(new CGRect(20, 250, 280, 44));
        label.setFont(UIFont.getSystemFont(24));
        label.setTextAlignment(NSTextAlignment.Center);
        view.addSubview(label);

        // Setup banner button.
        UIButton button = new UIButton(UIButtonType.RoundedRect);
        button.setFrame(new CGRect(20, 120, 280, 40));
        button.setTitle("Banner", UIControlState.Normal);
        button.getTitleLabel().setFont(UIFont.getBoldSystemFont(22));
        button.addOnTouchUpInsideListener((control, event) -> DemoCleverAds.demoBanner(this));
        view.addSubview(button);

        // Setup Interstitial button.
        button = new UIButton(UIButtonType.RoundedRect);
        button.setFrame(new CGRect(20, 170, 280, 40));
        button.setTitle("Interstitial", UIControlState.Normal);
        button.getTitleLabel().setFont(UIFont.getBoldSystemFont(22));
        button.addOnTouchUpInsideListener((control, event) -> DemoCleverAds.demoInterstitial(this));
        view.addSubview(button);
    }

    private void demo() {
        DemoCleverAds.demoBanner(this);
    }
}
