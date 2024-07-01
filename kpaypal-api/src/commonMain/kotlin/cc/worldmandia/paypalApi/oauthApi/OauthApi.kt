package cc.worldmandia.paypalApi.oauthApi

import cc.worldmandia.encodeToBase64
import cc.worldmandia.paypalApi.RequiredData
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

interface OauthApi : RequiredData {

    val oauthTokenType: String

    var accessTokenResponse: AccessTokenResponse

    fun enableUpdateAccessToken() = CoroutineScope(Dispatchers.Default).async {
        val logger = KotlinLogging.logger("accessTokenUpdater")
        while (true) {
            logger.debug { "Next token update after ${accessTokenResponse.expiresIn}" }
            delay((accessTokenResponse.expiresIn - 120).seconds)
            accessTokenResponse = getAccessToken()
        }
    }

    suspend fun getAccessToken(): AccessTokenResponse {
        return httpClient().post("v1/oauth2/token") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                append(
                    HttpHeaders.Authorization,
                    "$oauthTokenType ${encodeToBase64("${credentials().clientId}:${credentials().clientSecret}")}"
                )
            }
            setBody("grant_type=client_credentials")
        }.body<AccessTokenResponse>()
    }

}