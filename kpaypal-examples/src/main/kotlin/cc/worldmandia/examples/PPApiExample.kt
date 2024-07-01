package cc.worldmandia.examples

import cc.worldmandia.buildOrderRequest
import cc.worldmandia.buildPayPalClient
import cc.worldmandia.paypalApi.PayPalApi
import cc.worldmandia.paypalApi.PayPalLocale
import cc.worldmandia.paypalApi.orderApi.paymentSource.sources.paypal.LandingPage
import cc.worldmandia.paypalApi.orderApi.paymentSource.sources.paypal.PaymentMethodPreference
import cc.worldmandia.paypalApi.orderApi.paymentSource.sources.paypal.ShippingPreference
import cc.worldmandia.paypalApi.orderApi.paymentSource.sources.paypal.UserAction
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.coroutineScope
import java.lang.System.getenv

suspend fun main() = coroutineScope {

    val logger = KotlinLogging.logger {}

    buildPayPalClient {
        credentials {
            clientId = getenv("clientId").toString()
            clientSecret = getenv("clientSecret").toString()
        }
    }

    val data = PayPalApi.paypalClient.createOrder(buildOrderRequest {
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
                    locale = PayPalLocale.ENGLISH_US
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
    })

    logger.info {
        data.response.toString()
    }
}