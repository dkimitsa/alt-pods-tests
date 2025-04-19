package com.mycompany.myapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.robovm.apple.foundation.*
import org.robovm.pods.cocoatouch.storekitrvm.*
import org.robovm.pods.cocoatouch.storekitrvm.Product.SubscriptionInfo.RenewalState

// Abstract:
// The class responsible for requesting products from the App Store and starting purchases.

class StoreErrorFailedVerification: Exception()

// Define the app's subscription entitlements by level of service, with the highest level of service first.
// The numerical-level value matches the subscription's level that you configure in
// the StoreKit configuration file or App Store Connect.
enum class ServiceEntitlement {
    notEntitled,
    pro,
    premium,
    standard,
}

fun Product.toServiceEntitlement(): ServiceEntitlement? {
    if (subscription == null) return null
    return when (val id = id) {
        "subscription.standard" -> ServiceEntitlement.standard
        "subscription.premium" -> ServiceEntitlement.premium
        "subscription.pro" -> ServiceEntitlement.pro
        else -> ServiceEntitlement.notEntitled
    }
}

class Store(scope: CoroutineScope) {
    var currentSnapshot: Snapshot = Snapshot()
        private set(value) {
            field = value
            updateFlow.tryEmit(value)
        }

    private val updateFlow = MutableSharedFlow<Snapshot>(
        replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val snapshotFlow = updateFlow.asSharedFlow()

    data class Snapshot(
        val isLoaded: Boolean = false,
        val cars: List<Product> = listOf(),
        val fuel: List<Product> = listOf(),
        val subscriptions: List<Product> = listOf(),
        val nonRenewables: List<Product> = listOf(),
        val purchasedCars: List<Product> = listOf(),
        val purchasedNonRenewableSubscriptions: List<Product> = listOf(),
        val purchasedSubscriptions: List<Product> = listOf(),
        val subscriptionGroupStatus: Product.SubscriptionInfo.Status? = null
    )

    val productsIdToEmoji by lazy {
        mapOf(
            "consumable.fuel.octane87" to "⛽️",
            "consumable.fuel.octane89" to "⛽️",
            "consumable.fuel.octane91" to "⛽️",
            "nonconsumable.car" to "🚗",
            "nonconsumable.utilityvehicle" to "🚙",
            "nonconsumable.racecar" to "🏎",
            "subscription.standard" to "🗺",
            "subscription.premium" to "🗺",
            "subscription.pro" to "🗺",
            "nonRenewing.standard" to "🗺"
        )
    }

    init {
        // Start a transaction listener as close to app launch as possible so you don't miss any transactions.
        scope.launch {
            listenForTransactions()
        }

        // do a product request asynchronously to fill them in.
        scope.launch {
            // TODO: FIXME: REMOVE: delay to handle missing data at MyCarView
            delay(2000)

            // During store initialization, request products from the App Store.
            requestProducts()

            // Deliver products that the customer purchases.
            updateCustomerProductStatus()
        }
    }

    suspend fun listenForTransactions() {
        // Iterate through any transactions that don't come from a direct call to `purchase()`.
        Transaction.updates().forEach { result ->
            try {
                val transition = checkVerified(result)

                // Deliver products to the user.
                updateCustomerProductStatus()

                // Always finish a transaction.
                transition.finish()
            } catch (e: Exception) {
                // StoreKit has a transaction that fails verification. Don't deliver content to the user.
                e.printStackTrace()
                println("Transaction failed verification.")
            }
        }
    }

    suspend fun requestProducts() {
        try {
            // Request products from the App Store using the identifiers that the `Products.plist` file defines.
            val storeProducts = ProductKt.getProducts(productsIdToEmoji.keys)

            val newCars = mutableListOf<Product>()
            val newSubscriptions = mutableListOf<Product>()
            val newNonRenewables = mutableListOf<Product>()
            val newFuel = mutableListOf<Product>()

            // Filter the products into categories based on their type.
            storeProducts.forEach {
                when (it.type) {
                    Product.ProductType.consumable() -> newFuel.add(it)
                    Product.ProductType.nonConsumable() -> newCars.add(it)
                    Product.ProductType.autoRenewable() -> newSubscriptions.add(it)
                    Product.ProductType.nonRenewable() -> newNonRenewables.add(it)
                    else -> {
                        // Ignore this product.
                        println("Unknown product.")
                    }
                }
            }

            // Sort each product category by price, lowest to highest, to update the store.
            currentSnapshot = currentSnapshot.copy(
                isLoaded = true,
                cars = newCars.sortedByPrice(),
                subscriptions = newSubscriptions.sortedByPrice(),
                nonRenewables = newNonRenewables.sortedByPrice(),
                fuel = newFuel.sortedByPrice(),
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun purchase(product: Product): Transaction? {
        val result = product.purchase()
        return when (result) {
            is Product.PurchaseResult.success -> {
                val transition = checkVerified(result.transaction)
                updateCustomerProductStatus()
                transition.finish()
                transition
            }

            Product.PurchaseResult.pending(), Product.PurchaseResult.userCancelled() -> null
            else -> null
        }
    }

    fun isPurchased(product: Product): Boolean {
        // Determine whether the user purchases a given product.
        return when (product.type) {
            Product.ProductType.consumable() -> currentSnapshot.purchasedNonRenewableSubscriptions.contains(product)
            Product.ProductType.nonConsumable() -> currentSnapshot.purchasedCars.contains(product)
            Product.ProductType.autoRenewable() -> currentSnapshot.purchasedSubscriptions.contains(product)
            Product.ProductType.nonRenewable() -> false
            else -> false
        }
    }


    fun checkVerified(result: VerificationResult.Transaction): Transaction {
        return if (result.isVerified) result.payloadValue
        else throw StoreErrorFailedVerification()
    }
    fun checkVerified(result: VerificationResult.RenewalInfo): Product.SubscriptionInfo.RenewalInfo {
        return if (result.isVerified) result.payloadValue
        else throw StoreErrorFailedVerification()
    }

    suspend fun updateCustomerProductStatus() {
        val purchasedCars = mutableListOf<Product>()
        val purchasedSubscriptions = mutableListOf<Product>()
        val purchasedNonRenewableSubscriptions = mutableListOf<Product>()

        // Iterate through all of the user's purchased products.
        Transaction.currentEntitlements().forEach { result ->
            try {
                // Check whether the transaction is verified. If it isn’t, catch `failedVerification` error.
                val transaction = checkVerified(result)
                // Check the `productType` of the transaction and get the corresponding product from the store.
                when (transaction.productType) {
                    Product.ProductType.nonConsumable() -> {
                        currentSnapshot.cars.firstOrNull { it.id == transaction.productID }?.let { purchasedCars.add(it) }
                    }

                    Product.ProductType.nonRenewable() -> {
                        if (transaction.productID == "nonRenewing.standard") {
                            currentSnapshot.nonRenewables.firstOrNull { it.id == transaction.productID }?.let { nonRenewable ->
                                // Non-renewing subscriptions have no inherent expiration date, so `Transaction.currentEntitlements`
                                // always contains them after the user purchases them.
                                // This app defines this non-renewing subscription's expiration date to be one year after purchase.
                                // If the current date is within one year of the `purchaseDate`, the user is still entitled to this
                                // product.
                                val currentDate = NSDate()
                                val expirationDate =
                                    NSCalendar(NSCalendarIdentifier.Gregorian).newDateByAddingComponents(
                                        NSDateComponents().also { it.year = 1L }, transaction.purchaseDate,
                                        NSCalendarOptions.None
                                    )
                                // currentData < expirationDate
                                if (currentDate.compare(expirationDate) == NSComparisonResult.Ascending) {
                                    purchasedNonRenewableSubscriptions.add(nonRenewable)
                                }
                            }
                        }
                    }

                    Product.ProductType.autoRenewable() -> {
                        currentSnapshot.subscriptions.firstOrNull { it.id == transaction.productID }?.let { subscription ->
                            purchasedSubscriptions.add(subscription)
                        }
                    }

                    else -> {
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        currentSnapshot = currentSnapshot.copy(
            // Update the store information with the purchased products.
            purchasedCars = purchasedCars,
            purchasedNonRenewableSubscriptions = purchasedNonRenewableSubscriptions,

            // Update the store information with auto-renewable subscription products.
            purchasedSubscriptions = purchasedSubscriptions,

            // Check the `subscriptionGroupStatus` to learn the auto-renewable subscription state to determine whether the customer
            // is new (never subscribed), active, or inactive (expired subscription).
            // This app has only one subscription group, so products in the subscriptions array all belong to the same group.
            // Customers can be subscribed to only one product in the subscription group.
            // The statuses that `product.subscription.status` returns apply to the entire subscription group.
            subscriptionGroupStatus = currentSnapshot.subscriptions.firstOrNull()?.subscription?.getStatus()?.maxByOrNull {
                // There may be multiple statuses for different family members, because this app supports Family Sharing.
                // The subscriber is entitled to service for the status with the highest level of service.
                it.toEntitlement()
            }
        )
    }

    fun emoji(productId: String): String  = productsIdToEmoji[productId] ?: ":("

    private fun List<Product>.sortedByPrice(): List<Product> {
        return this.sortedWith { p0, p1 -> NSDecimal.compare(p0.price, p1.price).value().toInt() }
    }

    // Get a subscription's level of service using the product ID.
    private fun Product.SubscriptionInfo.Status.toEntitlement(): ServiceEntitlement {
        // If the status is expired, then the customer is not entitled.
        if (state == RenewalState.expired() || state == RenewalState.revoked()) {
            return ServiceEntitlement.notEntitled
        }
        // Get the product associated with the subscription status.
        val productID = transaction.unsafePayloadValue.productID
        val product = currentSnapshot.subscriptions.firstOrNull { it.id == productID } ?: return ServiceEntitlement.notEntitled

        // Finally, get the corresponding entitlement for this product.
        return product.toServiceEntitlement() ?: ServiceEntitlement.notEntitled
    }
}