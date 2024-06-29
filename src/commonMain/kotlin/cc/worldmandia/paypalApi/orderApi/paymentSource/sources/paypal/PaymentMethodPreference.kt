package cc.worldmandia.paypalApi.orderApi.paymentSource.sources.paypal

import kotlinx.serialization.Serializable

@Serializable
enum class PaymentMethodPreference {
    UNRESTRICTED,
    IMMEDIATE_PAYMENT_REQUIRED,
}