package cc.worldmandia.examples

import cc.worldmandia.buildOrderRequest
import cc.worldmandia.buildPayPalClient
import cc.worldmandia.paypalApi.PayPalApi
import cc.worldmandia.paypalApi.orderApi.paymentSource.sources.paypal.LandingPage
import cc.worldmandia.paypalApi.orderApi.paymentSource.sources.paypal.PaymentMethodPreference
import cc.worldmandia.paypalApi.orderApi.paymentSource.sources.paypal.ShippingPreference
import cc.worldmandia.paypalApi.orderApi.paymentSource.sources.paypal.UserAction
import io.klogging.config.getenv
import io.klogging.logger
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.util.*
import kotlin.time.Duration.Companion.seconds

suspend fun main() = coroutineScope {
    buildPayPalClient {
        credentials {
            clientId = getenv("clientId").toString()
            clientSecret = getenv("clientSecret").toString()
        }
    }

    val log = logger("PayPalApi")

    log.info {
        PayPalApi.paypalClient.createOrder(buildOrderRequest {
            purchaseUnit {
                amount {
                    currencyCode = "USD"
                    value = "50.00"
                }
                description = "My awesome product"
            }
            purchaseUnit {
                amount {
                    currencyCode = "USD"
                    value = "100.00"
                }
                description = "My awesome product 2"
            }

            paymentSource {
                payPalSource {
                    experienceContext {
                        brandName = "My Store"
                        returnUrl = "https://example.com/return"
                        cancelUrl = "https://example.com/cancel"
                        locale = Locale.ENGLISH
                        landingPage = LandingPage.GUEST_CHECKOUT
                        shippingPreference = ShippingPreference.NO_SHIPPING
                        userAction = UserAction.PAY_NOW
                        paymentMethodPreference = PaymentMethodPreference.UNRESTRICTED
                    }
                }
            }

            applicationContext {
                // TODO
            }
        }).response
    }

    // Wait response
    delay(3.seconds)
}