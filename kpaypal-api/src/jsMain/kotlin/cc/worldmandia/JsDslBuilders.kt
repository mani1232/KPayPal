package cc.worldmandia

import cc.worldmandia.paypalApi.PayPalClient
import io.ktor.client.engine.js.*

@PayPalDsl
suspend inline fun buildPayPalClient(block: PayPalClient.PayPalClientBuilder.() -> Unit): PayPalClient =
    PayPalClient.PayPalClientBuilder().apply(block).build(Js)