package cc.worldmandia.builders

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebHookCreateResponse(
    val id: String,
    val url: String,
    @SerialName("event_types")
    val eventTypes: List<EventType>,
    val links: List<Link>
) {
    @Serializable
    data class EventType(
        val name: String,
        val description: String
    )

    @Serializable
    data class Link(
        val href: String,
        val rel: String,
        val method: String
    )
}
