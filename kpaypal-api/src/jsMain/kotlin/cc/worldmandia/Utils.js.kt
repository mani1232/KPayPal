package cc.worldmandia

actual fun encodeToBase64(input: String): String {
    return js("btoa(input)") as String
}

actual fun generateUUID(): String {
    return js(
        """
        'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        })
    """
    ) as String
}