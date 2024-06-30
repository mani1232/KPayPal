package cc.worldmandia.paypalApi.orderApi

import cc.worldmandia.RequestPair
import cc.worldmandia.paypalApi.RequiredData
import cc.worldmandia.paypalApi.orderApi.builders.OrderRequest
import cc.worldmandia.paypalApi.orderApi.builders.OrderResponse
import cc.worldmandia.toRP
import io.klogging.Klogger
import io.klogging.logger
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

interface OrderApi : RequiredData {

    private val logger: Klogger
        get() = logger("OrderApi")

    fun createOrders(vararg orderRequest: OrderRequest): Flow<RequestPair<OrderRequest, OrderResponse?>> =
        orderRequest.asFlow().map {
            try {
                it toRP httpClient().post("v2/checkout/orders") {
                    headers {
                        append(HttpHeaders.Authorization, "${accessToken().tokenType} ${accessToken().accessToken}")
                    }
                    setBody(it)
                }.body<OrderResponse>()
            } catch (e: Exception) {
                logger.error("Filed to create order %s", e)
                it toRP null
            }
        }


    fun getOrders(vararg orderId: String): Flow<RequestPair<String, OrderResponse?>> =
        orderId.asFlow().map {
            try {
                it toRP httpClient().get("v2/checkout/orders/$it") {
                    headers {
                        append(HttpHeaders.Authorization, "${accessToken().tokenType} ${accessToken().accessToken}")
                    }
                }.body<OrderResponse>()
            } catch (e: Exception) {
                logger.error("Failed to get order %s", e)
                it toRP null
            }
        }

    fun updateOrders(vararg orderInfo: RequestPair<String, OrderRequest>): Flow<RequestPair<String, OrderResponse?>> =
        orderInfo.asFlow().map {
            try {
                it.request toRP httpClient().patch("v2/checkout/orders/${it.request}") {
                    headers {
                        append(HttpHeaders.Authorization, "${accessToken().tokenType} ${accessToken().accessToken}")
                    }
                    setBody(it.response)
                }.body<OrderResponse>()
            } catch (e: Exception) {
                logger.error("Failed to update order %s", e)
                it.request toRP null
            }
        }

    fun captureOrders(vararg orderId: String): Flow<RequestPair<String, OrderResponse?>> = orderId.asFlow().map {
        try {
            it toRP httpClient().post("v2/checkout/orders/$it/capture") {
                headers {
                    append(HttpHeaders.Authorization, "${accessToken().tokenType} ${accessToken().accessToken}")
                }
            }.body<OrderResponse>()
        } catch (e: Exception) {
            logger.error("Failed to capture order %s", e)
            it toRP null
        }
    }

    fun authorizeOrders(vararg orderId: String): Flow<RequestPair<String, OrderResponse?>> = orderId.asFlow().map {
        try {
            it toRP httpClient().post("v2/checkout/orders/$it/authorize") {
                headers {
                    append(HttpHeaders.Authorization, "${accessToken().tokenType} ${accessToken().accessToken}")
                }
            }.body<OrderResponse>()
        } catch (e: Exception) {
            logger.error("Failed to authorize order %s", e)
            it toRP null
        }
    }

    suspend infix fun createOrder(orderRequest: OrderRequest): RequestPair<OrderRequest, OrderResponse?> =
        createOrders(orderRequest).first()

    suspend infix fun getOrder(orderId: String): RequestPair<String, OrderResponse?> = getOrders(orderId).first()

    suspend infix fun captureOrder(orderId: String): RequestPair<String, OrderResponse?> =
        captureOrders(orderId).first()

    suspend infix fun authorizeOrder(orderId: String): RequestPair<String, OrderResponse?> =
        authorizeOrders(orderId).first()

    suspend infix fun updateOrder(orderId: RequestPair<String, OrderRequest>): RequestPair<String, OrderResponse?> =
        updateOrders(orderId).first()

}