package cc.worldmandia.paypalApi.orderApi.builders

import app.softwork.uuid.datetime.Uuidv7
import cc.worldmandia.OrderDsl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi

@Serializable
data class PurchaseUnit(
    val amount: Amount,
    val description: String? = null,
    @SerialName("custom_id") val customId: String? = null,
    @SerialName("reference_id") val referenceId: String,
    @SerialName("soft_descriptor") val softDescriptor: String? = null,
    val items: List<Item>? = null,
    @SerialName("invoice_id") val invoiceId: String? = null
) {

    class PurchaseUnitBuilder {
        private lateinit var amount: Amount
        var description: String? = null
        var customId: String? = null

        @OptIn(ExperimentalUuidApi::class)
        var referenceId: String = Uuidv7(random = Random).toString()
        var softDescriptor: String? = null
        var items: List<Item>? = null
        var invoiceId: String? = null

        @OrderDsl
        fun amount(block: Amount.AmountBuilder.() -> Unit) {
            amount = Amount.AmountBuilder().apply(block).build()
        }

        fun build(): PurchaseUnit {
            return PurchaseUnit(
                amount = amount,
                description = description,
                referenceId = referenceId,
                customId = customId,
                softDescriptor = softDescriptor,
                items = items,
                invoiceId = invoiceId
            )
        }

    }

    @Serializable
    data class Item(
        val name: String,
        val quantity: String,
        val description: String,
        val category: ItemCategory,
    )

    @Serializable
    enum class ItemCategory {
        DIGITAL_GOODS,
        PHYSICAL_GOODS,
        DONATION
    }

    @Serializable
    data class Amount(@SerialName("currency_code") val currencyCode: String, val value: String) {
        class AmountBuilder {
            lateinit var currencyCode: String
            lateinit var value: String

            fun build(): Amount {
                return Amount(currencyCode, value)
            }
        }
    }
}
