package cc.worldmandia.paypalApi.orderApi.paymentSource.sources.paypal

import kotlinx.serialization.Serializable

@Serializable
enum class UserAction {
    CONTINUE,
    PAY_NOW,
}