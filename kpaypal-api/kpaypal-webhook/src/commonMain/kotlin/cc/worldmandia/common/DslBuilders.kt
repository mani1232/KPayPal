package cc.worldmandia.common

import cc.worldmandia.builders.WebHookCreateRequest

@WebHookDsl
inline fun buildWebHookRequest(block: WebHookCreateRequest.WebhookRequestBuilder.() -> Unit): WebHookCreateRequest =
    WebHookCreateRequest.WebhookRequestBuilder().apply(block).build()