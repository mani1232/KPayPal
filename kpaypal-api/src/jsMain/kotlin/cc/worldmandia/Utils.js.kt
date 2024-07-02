package cc.worldmandia

actual fun encodeToBase64(input: String): String {
    return js("btoa(input)") as String
}

actual fun generateUUID(): String {
    return v4()
}

@JsModule("uuid")
@JsNonModule
external fun v4(): String