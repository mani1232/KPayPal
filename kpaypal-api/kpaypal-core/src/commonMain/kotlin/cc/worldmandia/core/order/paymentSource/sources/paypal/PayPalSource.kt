package cc.worldmandia.core.order.paymentSource.sources.paypal

import cc.worldmandia.common.OrderDsl
import cc.worldmandia.core.PayPalLocale
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("paypal")
data class PayPalSource(
    @SerialName("experience_context") val experienceContext: ExperienceContext? = null,
    @SerialName("billing_agreement_id") val billingAgreementId: String? = null,
    @SerialName("vault_id") val vaultId: String? = null,
    @SerialName("email_address") val emailAddress: String? = null,
    @SerialName("birth_date") val birthDate: String? = null,
) {

    class Builder {
        private var experienceContext: ExperienceContext? = null
        var billingAgreementId: String? = null
        var vaultId: String? = null
        var emailAddress: String? = null
        var birthDate: LocalDate? = null

        @OrderDsl
        fun experienceContext(block: ExperienceContext.Builder.() -> Unit) {
            experienceContext = ExperienceContext.Builder().apply(block).build()
        }

        fun build(): PayPalSource {
            return PayPalSource(
                experienceContext = experienceContext,
                billingAgreementId = billingAgreementId,
                vaultId = vaultId,
                emailAddress = emailAddress,
                birthDate = birthDate?.format(LocalDate.Formats.ISO),
            )
        }
    }

    @Serializable
    data class ExperienceContext(
        @SerialName("brand_name") val brandName: String? = null,
        @SerialName("shipping_preference") val shippingPreference: ShippingPreference? = null,
        @SerialName("landing_page") val landingPage: LandingPage? = null,
        @SerialName("user_action") val userAction: UserAction? = null,
        @SerialName("payment_method_preference") val paymentMethodPreference: PaymentMethodPreference? = null,
        @SerialName("locale") val locale: String? = null,
        @SerialName("return_url") val returnUrl: String? = null,
        @SerialName("cancel_url") val cancelUrl: String? = null,
    ) {

        class Builder {
            var brandName: String? = null
            var shippingPreference: ShippingPreference? = null
            var landingPage: LandingPage? = null
            var userAction: UserAction? = null
            var paymentMethodPreference: PaymentMethodPreference? = null
            var locale: PayPalLocale? = null
            var returnUrl: String? = null
            var cancelUrl: String? = null

            fun build(): ExperienceContext {
                return ExperienceContext(
                    brandName = brandName,
                    shippingPreference = shippingPreference,
                    landingPage = landingPage,
                    userAction = userAction,
                    paymentMethodPreference = paymentMethodPreference,
                    locale = locale?.languageTag,
                    returnUrl = returnUrl,
                    cancelUrl = cancelUrl,
                )
            }
        }
    }
}