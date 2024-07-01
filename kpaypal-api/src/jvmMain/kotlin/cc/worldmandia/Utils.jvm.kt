package cc.worldmandia

import java.util.*
import java.util.Base64.getEncoder

actual fun encodeToBase64(input: String): String {
    return getEncoder().encodeToString(input.toByteArray())
}

actual fun generateUUID(): String {
    return UUID.randomUUID().toString()
}