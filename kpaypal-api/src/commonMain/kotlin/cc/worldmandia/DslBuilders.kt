package cc.worldmandia

import cc.worldmandia.paypalApi.orderApi.builders.OrderRequest

@OrderDsl
inline fun buildOrderRequest(block: OrderRequest.OrderRequestBuilder.() -> Unit): OrderRequest =
    OrderRequest.OrderRequestBuilder().apply(block).build()