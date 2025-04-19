package com.mycompany.myapp.views

import com.mycompany.myapp.Store
import com.mycompany.myapp.dsl.TableDSL
import com.mycompany.myapp.dsl.UITableViewContentDSL
import com.mycompany.myapp.dsl.center
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.robovm.apple.uikit.UITableViewController
import org.robovm.pods.cocoatouch.storekitrvm.Product

class MyCarsViewController : UITableViewController() {
    val scope by lazy { CoroutineScope(Dispatchers.Default) }
    val store = Store(scope)

    override fun viewWillDisappear(animated: Boolean) {
        if (navigationController?.isBeingDismissed == true || isBeingDismissed) {
            scope.cancel()
        }
    }

    override fun viewDidLoad() {
        // observe updates
        scope.launch(Dispatchers.Main) {
            store.snapshotFlow.collect {
                delegate.submitData(it)
            }
        }
    }

    val delegate = UITableViewContentDSL(tableView, store.currentSnapshot) { store ->
        store ?: return@UITableViewContentDSL

        if (!store.isLoaded) {
            Text("Loading store items...")
            return@UITableViewContentDSL
        }

        if (store.purchasedCars.isEmpty() && store.purchasedNonRenewableSubscriptions.isEmpty() && store.purchasedSubscriptions.isEmpty()) {
            Text("SK Demo App").fontSize(50.0).center()
            Text("🏎💨").fontSize(120.0).center()
            Text("Head over to the shop to get started!").center()
            Action("🛒Shop") { StoreView() }.center()
        } else {
            Section("My Cars") {
                if (!store.purchasedCars.isEmpty()) {
                    store.purchasedCars.forEach { product -> ListCellView(product, purchasingEnabled = false) }
                } else {
                    Text("You don't own any car products. \nHead over to the shop to get started!")
                }
            }
            Section("Navigation Service") {
                if (!store.purchasedNonRenewableSubscriptions.isEmpty() || !store.purchasedSubscriptions.isEmpty()) {
                    store.purchasedNonRenewableSubscriptions.forEach { product ->
                        ListCellView(product, purchasingEnabled = false)
                    }
                    store.purchasedSubscriptions.forEach { product -> ListCellView(product, purchasingEnabled = false) }
                } else {
                    val subscriptionGroupStatus = store.subscriptionGroupStatus
                    if (subscriptionGroupStatus != null) {
                        if (subscriptionGroupStatus.state == Product.SubscriptionInfo.RenewalState.expired() || subscriptionGroupStatus.state == Product.SubscriptionInfo.RenewalState.revoked()) {
                            Text("Welcome Back! \nHead over to the shop to get started!")
                        } else if (subscriptionGroupStatus.state == Product.SubscriptionInfo.RenewalState.inBillingRetryPeriod()) {
                            // The best practice for subscriptions in the billing retry state is to provide a deep link
                            // from your app to https:// apps.apple.com/account/billing.
                            Text("Please verify your billing details.")
                        }
                    } else {
                        Text("You don't own any subscriptions. \nHead over to the shop to get started!")
                    }
                }
            }

            Action("🛒Shop") { StoreView() }
        }
    }

    private fun StoreView() {
        presentViewController(StoreViewController(store), true, null)
    }

    private fun TableDSL.SectionBuilder.ListCellView(product: Product, purchasingEnabled: Boolean) =
        ListCellView(product, purchasingEnabled, store, this@MyCarsViewController, scope)
}