package cc.worldmandia.paypalApi.orderApi.paymentSource.sources.paypal

import kotlinx.serialization.Serializable

@Serializable
enum class ShippingPreference {
    GET_FROM_FILE,
    NO_SHIPPING,
    SET_PROVIDED_ADDRESS,
}