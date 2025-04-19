package com.mycompany.myapp.views

import com.mycompany.myapp.Store
import com.mycompany.myapp.StoreErrorFailedVerification
import com.mycompany.myapp.dsl.TableDSL
import com.mycompany.myapp.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.robovm.apple.uikit.UIViewController
import org.robovm.pods.cocoatouch.storekitrvm.Product

// Abstract:
// A view for an individual car or subscription product that shows a Buy button when it displays within the store.
fun TableDSL.SectionBuilder.ListCellView(
    product: Product,
    purchasingEnabled: Boolean = true,
    store: Store,
    vc: UIViewController,
    scope: CoroutineScope
) {
    val emoji = store.emoji(product.id)
    val productDetail = if (product.type == Product.ProductType.autoRenewable()) {
        product.displayName + "\n" + product.description()
    } else product.description()
    var buyButton = ""
    if (purchasingEnabled) {
        val isPurchased = store.isPurchased(product)
        buyButton = if (isPurchased)  "✓" else {
            if (product.subscription != null) {
                subscribeButton(product)
            } else {
                product.displayPrice
            }
        }
    }

    fun buy() = scope.launch(Dispatchers.Main) {
        try {
            store.purchase(product)
        } catch (e: StoreErrorFailedVerification) {
            vc.showToast("Your purchase could not be verified by the App Store.")
        } catch (e: Exception) {
            e.printStackTrace()
            vc.showToast("Failed purchase for ${product.id}. ${e.message}")
        }
    }

    SubtitleText("$emoji $productDetail", buyButton)
        .detailFontSize(8.0)
        .onClick { buy() }
}

private fun subscribeButton(product: Product): String {
    val subscription = product.subscription
    val plural = subscription.subscriptionPeriod.value > 1
    val unit = when (subscription.subscriptionPeriod.unit) {
        Product.SubscriptionPeriod.Unit.Day -> if (plural) "${subscription.subscriptionPeriod.value}) days" else "day"
        Product.SubscriptionPeriod.Unit.Week -> if (plural) "${subscription.subscriptionPeriod.value}) weeks" else "week"
        Product.SubscriptionPeriod.Unit.Month -> if (plural) "${subscription.subscriptionPeriod.value}) months" else "month"
        Product.SubscriptionPeriod.Unit.Year -> if (plural) "${subscription.subscriptionPeriod.value}) years" else "year"
        else -> "period"
    }
    return product.displayPrice + "\n" + unit
}

