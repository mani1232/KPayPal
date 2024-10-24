package cc.worldmandia.common

actual fun encodeToBase64(input: String): String {
    return js("btoa(input)") as String
}