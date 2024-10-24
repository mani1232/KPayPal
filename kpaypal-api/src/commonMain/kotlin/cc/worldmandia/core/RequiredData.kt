package cc.worldmandia.core

import cc.worldmandia.core.oauth.AccessTokenResponse
import cc.worldmandia.core.oauth.builders.PayPalCredentials
import io.ktor.client.*

interface RequiredData {
    fun httpClient(): HttpClient

    fun accessToken(): AccessTokenResponse

    fun credentials(): PayPalCredentials

}