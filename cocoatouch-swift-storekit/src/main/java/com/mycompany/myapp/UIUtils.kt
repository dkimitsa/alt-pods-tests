package com.mycompany.myapp

import org.robovm.apple.dispatch.DispatchQueue
import org.robovm.apple.foundation.NSDate
import org.robovm.apple.foundation.NSLocale
import org.robovm.apple.uikit.UIAlertController
import org.robovm.apple.uikit.UIAlertControllerStyle
import org.robovm.apple.uikit.UIViewController
import java.util.concurrent.TimeUnit

fun UIViewController.showToast(message: String) {
    val alert = UIAlertController(null, message, UIAlertControllerStyle.Alert)
    alert.view.layer.cornerRadius = 15.0

    presentViewController(alert, true, {})
    DispatchQueue.getMainQueue().after(2L, TimeUnit.SECONDS) {
        alert.dismissViewController(true, {})
    }
}

fun NSDate.formattedDate(): String = toString(NSLocale.getCurrentLocale())