package cc.worldmandia.paypalApi

import cc.worldmandia.paypalApi.oauthApi.AccessTokenResponse
import cc.worldmandia.paypalApi.oauthApi.builders.PayPalCredentials
import io.ktor.client.*

interface RequiredData {
    fun httpClient(): HttpClient

    fun accessToken(): AccessTokenResponse

    fun credentials(): PayPalCredentials

}