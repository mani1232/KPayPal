package cc.worldmandia.paypalApi.oauthApi

import cc.worldmandia.paypalApi.RequiredData
import io.klogging.logger
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds

interface OauthApi : RequiredData {

    val oauthTokenType: String

    var accessTokenResponse: AccessTokenResponse

    fun enableUpdateAccessToken() = CoroutineScope(Dispatchers.Default).async {
        val tuLogger = logger("TokenUpdater")
        while (true) {
            tuLogger.debug {
                "Next token update after ${accessTokenResponse.expiresIn}"
            }
            delay((accessTokenResponse.expiresIn - 120).seconds)
            accessTokenResponse = getAccessToken()
        }
    }

    fun getAccessToken(): AccessTokenResponse = runBlocking {
        return@runBlocking httpClient().post("v1/oauth2/token") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                append(HttpHeaders.Authorization, "$oauthTokenType ${credentials().encodeToBase64()}")
            }
            setBody("grant_type=client_credentials")
        }.body()
    }
}