package cc.worldmandia.core

import cc.worldmandia.common.PayPalDsl
import cc.worldmandia.core.oauth.AccessTokenResponse
import cc.worldmandia.core.oauth.OauthApi
import cc.worldmandia.core.oauth.builders.PayPalCredentials
import cc.worldmandia.core.order.OrderApi
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Job
import kotlinx.serialization.json.Json

data class PayPalClient(
    private val credentials: PayPalCredentials,
    private val engine: HttpClientEngineFactory<HttpClientEngineConfig>,
    private val customJson: Json = Json {
        encodeDefaults = true
        isLenient = true
        allowSpecialFloatingPointValues = true
        allowStructuredMapKeys = true
        prettyPrint = true
        useArrayPolymorphism = true
        ignoreUnknownKeys = true
    },
    private val httpClient: HttpClient = HttpClient(engine) {
        defaultRequest {
            url(if (credentials.isSandbox) "https://api-m.sandbox.paypal.com/" else "https://api-m.paypal.com/")
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
        install(ContentNegotiation) {
            json(customJson)
        }
    }
) : OrderApi, OauthApi {

    class PayPalClientBuilder {
        private lateinit var credentials: PayPalCredentials
        var engine: HttpClientEngineFactory<HttpClientEngineConfig>? = null

        @PayPalDsl
        fun credentials(block: PayPalCredentials.PayPalCredentialsBuilder.() -> Unit) {
            credentials = PayPalCredentials.PayPalCredentialsBuilder().apply(block).build()
        }

        suspend fun build(defaultEngine: HttpClientEngineFactory<HttpClientEngineConfig>): PayPalClient {
            val client = PayPalClient(
                credentials,
                engine ?: defaultEngine
            )
            client.accessTokenResponse = client.getAccessToken()
            return client
        }
    }

    init {
        PayPalApi.paypalClient = this
    }

    private var updateTokenJob: Job = enableUpdateAccessToken()

    override val oauthTokenType: String
        get() = credentials.oauthTokenType

    override lateinit var accessTokenResponse: AccessTokenResponse

    fun close() {
        updateTokenJob.cancel()
        httpClient.close()
    }

    override fun httpClient(): HttpClient {
        return httpClient
    }

    override fun accessToken(): AccessTokenResponse {
        return accessTokenResponse
    }

    override fun credentials(): PayPalCredentials {
        return credentials
    }
}