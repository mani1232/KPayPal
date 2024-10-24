package cc.worldmandia.core.order.paymentSource.sources.paypal

import kotlinx.serialization.Serializable

@Serializable
enum class UserAction {
    CONTINUE,
    PAY_NOW,
}