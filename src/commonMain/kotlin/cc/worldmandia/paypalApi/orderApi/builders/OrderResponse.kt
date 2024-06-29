package cc.worldmandia.paypalApi.orderApi.builders

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val id: String,
    val status: String,
    val links: List<Link>,
    @SerialName("purchase_units") val purchaseUnits: List<PurchaseUnit>? = null,
    val payer: Payer? = null
) {

    @Serializable
    data class Link(val href: String, val rel: String, val method: String)

    @Serializable
    data class PayerName(@SerialName("given_name") val givenName: String, val surname: String)

    @Serializable
    data class Payer(
        val name: PayerName? = null,
        @SerialName("email_address") val emailAddress: String? = null,
        @SerialName("payer_id") val payerId: String? = null
    )
}
