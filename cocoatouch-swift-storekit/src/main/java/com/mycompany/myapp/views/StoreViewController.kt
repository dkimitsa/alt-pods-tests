package com.mycompany.myapp.views

import com.mycompany.myapp.Store
import com.mycompany.myapp.dsl.TableDSL
import com.mycompany.myapp.dsl.UITableViewContentDSL
import com.mycompany.myapp.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.robovm.apple.uikit.UITableViewController
import org.robovm.apple.uikit.UITableViewStyle
import org.robovm.pods.cocoatouch.storekitrvm.AppStoreKt
import org.robovm.pods.cocoatouch.storekitrvm.Product
import org.robovm.pods.cocoatouch.storekitrvm.sync

/**
 * Abstract:
 * The view for the store.
 */
class StoreViewController(private val store: Store) : UITableViewController(UITableViewStyle.Grouped) {

    val scope by lazy { CoroutineScope(Dispatchers.Default) }

    override fun viewWillDisappear(animated: Boolean) {
        if (navigationController?.isBeingDismissed == true || isBeingDismissed) {
            scope.cancel()
        }
    }

    val combinedFlow = combine(
        store.snapshotFlow,
        store.snapshotFlow.map { updateSubscriptionStatus(store) }) { storeSnapshot, subscriptionStatus ->
        storeSnapshot to subscriptionStatus
    }

    override fun viewDidLoad() {
        // collect both store data and subscription status
        scope.launch(Dispatchers.Main)  {
            combinedFlow.collect(delegate::submitData)
        }
    }

    val delegate = UITableViewContentDSL(tableView, Pair(store.currentSnapshot, null as SubscriptionStatus?)) { pair ->
        pair ?: return@UITableViewContentDSL
        val (store, subscriptionStatus) = pair

        Section("Cars") {
            store.cars.forEach { car -> ListCellView(car,) }
        }

        SubscriptionsView(subscriptionStatus, this@StoreViewController.store, this@StoreViewController, scope)

        Section("Navigation: Non-Renewing Subscription") {
            store.nonRenewables.forEach { car -> ListCellView(car) }
        }

        Action("🛒Restore Purchases") {
            scope.launch(Dispatchers.Main) {
                try {
                    AppStoreKt.sync()
                    showToast("AppStore.sync complete")
                } catch (e: Exception) {
                    showToast("AppStore.sync failed: ${e.message}")
                }
            }
        }
    }

    private fun TableDSL.SectionBuilder.ListCellView(product: Product) =
        ListCellView(product, purchasingEnabled = true, store, this@StoreViewController, scope)
}