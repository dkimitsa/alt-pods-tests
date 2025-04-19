package com.mycompany.myapp.views

import com.mycompany.myapp.Store
import com.mycompany.myapp.dsl.TableDSL
import com.mycompany.myapp.dsl.center
import com.mycompany.myapp.formattedDate
import org.robovm.apple.foundation.NSComparisonResult
import org.robovm.apple.foundation.NSDate
import org.robovm.pods.cocoatouch.storekitrvm.Product
import org.robovm.pods.cocoatouch.storekitrvm.Product.SubscriptionInfo.RenewalInfo

/**
 * Abstract:
 * A view to display a status in plain text.
 */

fun TableDSL.SectionBuilder.StatusInfoView(product: Product, status: Product.SubscriptionInfo.Status, store: Store) {

    fun billingRetryDescription() = "The App Store could not confirm your billing information for ${product.displayName}." +
        " Please verify your billing information to resume service."

    fun gracePeriodDescription(renewalInfo: RenewalInfo): String {
        var description = "The App Store could not confirm your billing information for ${product.displayName}."
        val untilDate = renewalInfo.gracePeriodExpirationDate
        if (untilDate != null){
            description += " Please verify your billing information to continue service after ${untilDate.formattedDate()})"
        }

        return description
    }

    fun subscribedDescription(): String = "You are currently subscribed to ${product.displayName}."

    fun renewalDescription(renewalInfo: RenewalInfo, expirationDate: NSDate): String {
        var description = ""

        val newProductID = renewalInfo.autoRenewPreference
        if (newProductID != null) {
            store.currentSnapshot.subscriptions.firstOrNull { it.id == newProductID }?.let { newProduct ->
                description += "\nYour subscription to ${newProduct.displayName}"
                description += " will begin when your current subscription expires on ${expirationDate.formattedDate()}."
            }
        } else if (renewalInfo.isWillAutoRenew) {
            description += "\nNext billing date: ${expirationDate.formattedDate()}."
        }

        return description
    }

    // Build a string description of the `expirationReason` to display to the user.
    fun expirationDescription(expirationReason: RenewalInfo.ExpirationReason, expirationDate: NSDate): String {
        var description = ""

        when (expirationReason) {
            RenewalInfo.ExpirationReason.autoRenewDisabled() -> {
                if (NSDate.now().compare(expirationDate) == NSComparisonResult.Ascending) { // expirationDate > Date()
                    description += "Your subscription to ${product.displayName} will expire ${expirationDate.formattedDate()}."
                } else {
                    description += "Your subscription to ${product.displayName} expired on ${expirationDate.formattedDate()}."
                }
            }
            RenewalInfo.ExpirationReason.billingError() ->
                description = "Your subscription to ${product.displayName} was not renewed due to a billing error."
            RenewalInfo.ExpirationReason.didNotConsentToPriceIncrease() ->
                description = "Your subscription to ${product.displayName} was not renewed due to a price increase that you disapproved."
            RenewalInfo.ExpirationReason.productUnavailable() ->
                description = "Your subscription to ${product.displayName} was not renewed because the product is no longer available."
            else -> description = "Your subscription to ${product.displayName} was not renewed."
        }

        return description
    }


    // Build a string description of the subscription status to display to the user.
    fun statusDescription(): String {
        if (!status.renewalInfo.isVerified || !status.transaction.isVerified)
            return "The App Store could not verify your subscription status."
        val transaction = status.transaction.payloadValue
        val renewalInfo = status.renewalInfo.payloadValue

        var description = ""

        when (status.state) {
            Product.SubscriptionInfo.RenewalState.subscribed() -> description = subscribedDescription()
            Product.SubscriptionInfo.RenewalState.expired() -> {
                val expirationDate = transaction.expirationDate
                val expirationReason = renewalInfo.expirationReason
                if (expirationDate != null && expirationReason != null) {
                    description = expirationDescription(expirationReason, expirationDate)
                }
            }
            Product.SubscriptionInfo.RenewalState.revoked() -> {
                val revokedDate = transaction.revocationDate
                if (revokedDate != null) {
                    description =
                        "The App Store refunded your subscription to ${product.displayName} on ${revokedDate.formattedDate()}."
                }
            }
            Product.SubscriptionInfo.RenewalState.inGracePeriod() -> {
                description = gracePeriodDescription(renewalInfo)
            }

            Product.SubscriptionInfo.RenewalState.inBillingRetryPeriod() -> {
                description = billingRetryDescription()
            }
            else -> {}
        }

        val expirationDate = transaction.expirationDate
        if (expirationDate != null) {
            description += renewalDescription(renewalInfo, expirationDate)
        }
        return description
    }

    Text(statusDescription()).center()
}