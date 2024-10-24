package cc.worldmandia.core.order.builders

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApplicationContext(
    @SerialName("stored_payment_source") val storedPaymentSource: String? = null,
) {

    class ApplicationContextBuilder {
        var storedPaymentSource: String? = null

        fun build(): ApplicationContext = ApplicationContext(
            storedPaymentSource = storedPaymentSource
        )
    }

}