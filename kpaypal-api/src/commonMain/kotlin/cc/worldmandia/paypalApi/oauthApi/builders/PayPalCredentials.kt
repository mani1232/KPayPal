package cc.worldmandia.paypalApi.oauthApi.builders

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
}
