package cc.worldmandia

import cc.worldmandia.paypalApi.PayPalClient
import cc.worldmandia.paypalApi.orderApi.builders.OrderRequest

@OrderDsl
inline fun buildOrderRequest(block: OrderRequest.OrderRequestBuilder.() -> Unit): OrderRequest =
    OrderRequest.OrderRequestBuilder().apply(block).build()

@PayPalDsl
inline fun buildPayPalClient(block: PayPalClient.PayPalClientBuilder.() -> Unit): PayPalClient =
    PayPalClient.PayPalClientBuilder().apply(block).build()