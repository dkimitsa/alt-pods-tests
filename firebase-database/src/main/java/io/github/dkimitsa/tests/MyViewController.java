package io.github.dkimitsa.tests;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.uikit.*;

public class MyViewController extends UIViewController {
    private final UIButton button;
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

        // Setup button.
        button = new UIButton(UIButtonType.RoundedRect);
        button.setFrame(new CGRect(110, 150, 100, 40));
        button.setTitle("Demo.", UIControlState.Normal);
        button.getTitleLabel().setFont(UIFont.getBoldSystemFont(22));

        button.addOnTouchUpInsideListener((control, event) -> demo());
        view.addSubview(button);
    }

    private void demo() {
        DemoFirebaseDB.demo();
    }
}
