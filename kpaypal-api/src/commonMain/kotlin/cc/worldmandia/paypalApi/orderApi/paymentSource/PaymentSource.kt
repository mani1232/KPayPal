package cc.worldmandia.paypalApi.orderApi.paymentSource

import cc.worldmandia.OrderDsl
import cc.worldmandia.paypalApi.orderApi.paymentSource.sources.paypal.PayPalSource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentSource(
    @SerialName("paypal") var payPalSource: PayPalSource? = null
) {

    class Builder {
        private var payPalSource: PayPalSource? = null

        @OrderDsl
        fun payPalSource(block: PayPalSource.Builder.() -> Unit) {
            payPalSource = PayPalSource.Builder().apply(block).build()
        }

        fun build(): PaymentSource {
            return PaymentSource(payPalSource = payPalSource)
        }
    }
}