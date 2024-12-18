package webhook.event

import kotlinx.serialization.Serializable

@Serializable
data class PaymentAuthorizationCreatedEvent(
    val str: String,
): WebHookEvent
