package cc.worldmandia.core.order.paymentSource.sources.paypal

import kotlinx.serialization.Serializable

@Serializable
enum class ShippingPreference {
    GET_FROM_FILE,
    NO_SHIPPING,
    SET_PROVIDED_ADDRESS,
}