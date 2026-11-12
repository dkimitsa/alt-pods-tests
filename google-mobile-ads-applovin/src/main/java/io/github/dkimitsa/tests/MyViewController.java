package io.github.dkimitsa.tests;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.uikit.*;

public class MyViewController extends UIViewController {
    private final UIButton button;
    private final UILabel label;

    public MyViewController() {
        UIView view = getView();
        view.setBackgroundColor(UIColor.white());

        label = new UILabel(new CGRect(20, 250, 280, 44));
        label.setFont(UIFont.getSystemFont(24));
        label.setTextAlignment(NSTextAlignment.Center);
        view.addSubview(label);

        button = new UIButton(UIButtonType.RoundedRect);
        button.setFrame(new CGRect(110, 150, 100, 40));
        button.setTitle("Demo.", UIControlState.Normal);
        button.getTitleLabel().setFont(UIFont.getBoldSystemFont(22));

        button.addOnTouchUpInsideListener((control, event) -> demo());
        view.addSubview(button);
    }

    private void demo() {
        DemoGmaAppLovin.demo();
    }
}
