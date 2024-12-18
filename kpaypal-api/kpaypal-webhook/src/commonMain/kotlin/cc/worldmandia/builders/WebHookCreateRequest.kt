package cc.worldmandia.builders

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebHookCreateRequest(
    val url: String,
    @SerialName("event_types")
    val eventTypes: List<EventTypesData>,
) {

    class WebhookRequestBuilder {
        lateinit var url: String
        val eventTypes: List<EventType> = emptyList()

        fun build(): WebHookCreateRequest {
            require(eventTypes.isEmpty()) { "Event types cannot be empty" }

            return WebHookCreateRequest(
                url = url,
                eventTypes = eventTypes.map { type ->
                    EventTypesData(type.value)
                }
            )
        }
    }

    @Serializable
    data class EventTypesData(
        val name: String,
    )

    enum class EventType(val value: String) {
        PAYMENT_AUTHORIZATION_CREATED("PAYMENT.AUTHORIZATION.CREATED"),
        PAYMENT_AUTHORIZATION_VOIDED("PAYMENT.AUTHORIZATION.VOIDED")
    }

}