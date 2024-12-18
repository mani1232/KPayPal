package webhook

import cc.worldmandia.builders.WebHookCreateRequest
import cc.worldmandia.builders.WebHookCreateResponse
import cc.worldmandia.common.RequestPair
import cc.worldmandia.common.toRP
import cc.worldmandia.core.PayPalApi
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

object PPWebHookApi {

    private val logger: KLogger
        get() = KotlinLogging.logger("PPWebHookApi")

    suspend fun createWebhook(vararg webhooksRequests: WebHookCreateRequest): Flow<RequestPair<WebHookCreateRequest, WebHookCreateResponse?>> {
        return webhooksRequests.asFlow().map { request ->
            try {
                request toRP PayPalApi.paypalClient.httpClient().post("v1/notifications/webhooks") {
                    headers {
                        append(HttpHeaders.Authorization, "${PayPalApi.paypalClient.accessToken().tokenType} ${PayPalApi.paypalClient.accessToken().accessToken}")
                    }
                    setBody(request)
                }.body<WebHookCreateResponse>()
            } catch (e: Exception) {
                logger.error(e) { "Error while requesting orders" }
                request toRP null
            }
        }
    }
}