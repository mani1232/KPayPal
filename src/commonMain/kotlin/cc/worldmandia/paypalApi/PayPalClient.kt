package cc.worldmandia.paypalApi

import cc.worldmandia.PayPalDsl
import cc.worldmandia.paypalApi.oauthApi.AccessTokenResponse
import cc.worldmandia.paypalApi.oauthApi.OauthApi
import cc.worldmandia.paypalApi.oauthApi.builders.PayPalCredentials
import cc.worldmandia.paypalApi.orderApi.OrderApi
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Job
import kotlinx.serialization.json.Json

data class PayPalClient(
    private val credentials: PayPalCredentials,
    private val httpClient: HttpClient = HttpClient(CIO) {
        defaultRequest {
            url(if (credentials.isSandbox) "https://api-m.sandbox.paypal.com/" else "https://api-m.paypal.com/")
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                isLenient = true
                allowSpecialFloatingPointValues = true
                allowStructuredMapKeys = true
                prettyPrint = true
                useArrayPolymorphism = true
                ignoreUnknownKeys = true
            })
        }
    }
) : OrderApi, OauthApi {

    class PayPalClientBuilder {
        private lateinit var credentials: PayPalCredentials

        @PayPalDsl
        fun credentials(block: PayPalCredentials.PayPalCredentialsBuilder.() -> Unit) {
            credentials = PayPalCredentials.PayPalCredentialsBuilder().apply(block).build()
        }

        fun build(): PayPalClient {
            return PayPalClient(
                credentials
            )
        }
    }

    init {
        PayPalApi.paypalClient = this
    }

    private var localAccessTokenResponse: AccessTokenResponse = getAccessToken()

    private var updateTokenJob: Job = enableUpdateAccessToken()

    override val oauthTokenType: String
        get() = credentials.oauthTokenType

    override var accessTokenResponse: AccessTokenResponse
        get() = localAccessTokenResponse
        set(value) {
            localAccessTokenResponse = value
        }

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