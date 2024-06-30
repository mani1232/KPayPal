package cc.worldmandia.paypalApi.oauthApi.builders

import java.util.Base64.getEncoder

data class PayPalCredentials(
    val clientId: String,
    val clientSecret: String,
    val isSandbox: Boolean = true,
    val oauthTokenType: String
) {

    class PayPalCredentialsBuilder {
        lateinit var clientId: String
        lateinit var clientSecret: String
        val isSandbox: Boolean = true
        val oauthTokenType: String = "Basic"

        fun build(): PayPalCredentials {
            return PayPalCredentials(clientId, clientSecret, isSandbox, oauthTokenType)
        }
    }

    fun encodeToBase64(): String {
        return getEncoder().encodeToString("$clientId:$clientSecret".toByteArray())
    }
}
