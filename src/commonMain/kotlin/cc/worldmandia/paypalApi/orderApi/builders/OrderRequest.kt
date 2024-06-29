package cc.worldmandia.paypalApi.orderApi.builders

import cc.worldmandia.OrderDsl
import cc.worldmandia.paypalApi.orderApi.paymentSource.PaymentSource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class OrderRequest(
    val intent: Intent,
    @SerialName("purchase_units")
    val purchaseUnits: List<PurchaseUnit>,
    @SerialName("application_context")
    val applicationContext: ApplicationContext,
    @SerialName("payment_source")
    val paymentSource: PaymentSource?
) {

    class OrderRequestBuilder {
        var intent: Intent = Intent.CAPTURE
        private val purchaseUnits: MutableList<PurchaseUnit> = mutableListOf()
        private var applicationContext: ApplicationContext? = null
        private var paymentSource: PaymentSource? = null

        @OrderDsl
        fun purchaseUnit(block: PurchaseUnit.PurchaseUnitBuilder.() -> Unit) {
            purchaseUnits.add(PurchaseUnit.PurchaseUnitBuilder().apply(block).build())
        }

        @OrderDsl
        fun paymentSource(block: PaymentSource.Builder.() -> Unit) {
            paymentSource = PaymentSource.Builder().apply(block).build()
        }

        @OrderDsl
        fun applicationContext(block: ApplicationContext.ApplicationContextBuilder.() -> Unit) {
            applicationContext = ApplicationContext.ApplicationContextBuilder().apply(block).build()
        }

        fun build(): OrderRequest {
            if (intent == Intent.AUTHORIZE) {
                require(purchaseUnits.size == 1) { "purchase_units should be 1, but ${purchaseUnits.size} != 1" }
            }

            return OrderRequest(
                intent = intent,
                purchaseUnits = purchaseUnits,
                applicationContext = applicationContext ?: error("ApplicationContext is required"),
                paymentSource = paymentSource
            )
        }
    }

    @Serializable
    enum class Intent {
        CAPTURE,
        AUTHORIZE
    }

}