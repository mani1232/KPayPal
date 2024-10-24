package cc.worldmandia.common

import cc.worldmandia.core.PayPalClient
import io.ktor.client.engine.js.*

@PayPalDsl
suspend inline fun buildPayPalClient(block: PayPalClient.PayPalClientBuilder.() -> Unit): PayPalClient =
    PayPalClient.PayPalClientBuilder().apply(block).build(Js)