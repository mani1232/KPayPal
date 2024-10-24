package cc.worldmandia.common

import cc.worldmandia.core.order.builders.OrderRequest

@OrderDsl
inline fun buildOrderRequest(block: OrderRequest.OrderRequestBuilder.() -> Unit): OrderRequest =
    OrderRequest.OrderRequestBuilder().apply(block).build()