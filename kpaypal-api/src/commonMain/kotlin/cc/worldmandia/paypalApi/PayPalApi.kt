package cc.worldmandia.paypalApi

object PayPalApi {
    private lateinit var PayPalClient: PayPalClient

    var paypalClient: PayPalClient
        get() = PayPalClient
        set(value) {
            PayPalClient = value
        }
}