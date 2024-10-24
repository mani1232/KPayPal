package cc.worldmandia.core.order.paymentSource.sources.paypal

import kotlinx.serialization.Serializable

@Serializable
enum class LandingPage {
    LOGIN,
    GUEST_CHECKOUT,
    NO_PREFERENCE,
}