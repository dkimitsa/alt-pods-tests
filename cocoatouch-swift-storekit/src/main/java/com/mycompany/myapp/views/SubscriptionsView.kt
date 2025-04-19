package com.mycompany.myapp.views

import com.mycompany.myapp.Store
import com.mycompany.myapp.dsl.TableDSL
import com.mycompany.myapp.toServiceEntitlement
import kotlinx.coroutines.CoroutineScope
import org.robovm.apple.uikit.UIViewController
import org.robovm.pods.cocoatouch.storekitrvm.Product
import org.robovm.pods.cocoatouch.storekitrvm.Product.SubscriptionInfo.RenewalState
import org.robovm.pods.cocoatouch.storekitrvm.getStatus

/**
 * Abstract:
 * A view in the store for subscription products that also displays subscription statuses.
 */

data class SubscriptionStatus(
    val currentSubscription: Product,
    val status: Product.SubscriptionInfo.Status
)


fun TableDSL.TableBuilder.SubscriptionsView(
    subscriptionStatus: SubscriptionStatus?,
    store: Store,
    vc: UIViewController,
    scope: CoroutineScope
) {
    val currentSubscription = subscriptionStatus?.currentSubscription
    val status = subscriptionStatus?.status

    val availableSubscriptions by lazy {
        store.currentSnapshot.subscriptions.filter { it.id != currentSubscription?.id }
    }

    if (currentSubscription != null) {
        Section("My Subscription") {
            ListCellView(product = currentSubscription, purchasingEnabled = false, store, vc, scope)

            if (status != null) {
                StatusInfoView(product = currentSubscription, status, store)
            }
        }
    }

    Section("Navigation: Auto-Renewable Subscription") {
        for (product in availableSubscriptions) {
            ListCellView(product, purchasingEnabled = true, store, vc, scope)
        }
    }
}


suspend fun updateSubscriptionStatus(store: Store): SubscriptionStatus? {
    try {
        // This app has only one subscription group, so products in the subscriptions
        // array all belong to the same group. The statuses that
        // `product.subscription.status` returns apply to the entire subscription group.
        val statuses = store.currentSnapshot.subscriptions.firstOrNull()?.let { product ->
            product.subscription?.getStatus()
        } ?: return null

        var highestStatus: Product.SubscriptionInfo.Status? = null
        var highestProduct: Product? = null

        // Iterate through `statuses` for this subscription group,
        // and find the `Status` with the highest level of service that isn't
        // in an expired or revoked state. For example, a customer may have status
        // values corresponding to different levels of service through Family Sharing.

        for (status in statuses) {
            when (status.state) {
                RenewalState.expired(), RenewalState.revoked() -> continue
                else -> {
                    val renewalInfo = store.checkVerified(status.renewalInfo)

                    // Find the first subscription product that matches the subscription status renewal info by comparing the product IDs.
                    val newSubscription =
                        store.currentSnapshot.subscriptions.firstOrNull { it.id == renewalInfo.currentProductID }
                            ?: continue

                    val currentProduct = highestProduct
                    if (currentProduct == null) {
                        highestStatus = status
                        highestProduct = newSubscription
                        continue
                    }

                    val highestEntitlement = currentProduct.toServiceEntitlement()
                        ?: continue
                    val newEntitlement = newSubscription.toServiceEntitlement()
                        ?: continue

                    if (newEntitlement > highestEntitlement) {
                        highestStatus = status
                        highestProduct = newSubscription
                    }
                }
            }
        }

        return if (highestStatus != null && highestProduct != null)
            SubscriptionStatus(status = highestStatus, currentSubscription = highestProduct) else null
    } catch (e: Exception) {
        e.printStackTrace()
        println("Could not update subscription status. ${e.message}")
        return null
    }
}
