package cc.worldmandia.core.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Long,
    @SerialName("token_type") val tokenType: String,
    @SerialName("app_id") val appId: String,
    @SerialName("nonce") val nonce: String,
    @SerialName("scope") val scope: String,
)
