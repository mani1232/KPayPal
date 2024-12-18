package webhook

import webhook.event.PaymentAuthorizationCreatedEvent
import webhook.event.WebhookEventBus

class WebHookServer{
    val webhookEventBus = WebhookEventBus()
}

suspend fun main() {
    val webHookServer = WebHookServer()

    webHookServer.webhookEventBus.subscribe<PaymentAuthorizationCreatedEvent> { event ->
        println("Hello WebHookServer ${event.str}!")
    }

    webHookServer.webhookEventBus.publish(PaymentAuthorizationCreatedEvent("WEEEEE"))

}