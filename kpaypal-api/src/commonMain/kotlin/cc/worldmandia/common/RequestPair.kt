package cc.worldmandia.common

data class RequestPair<out A, out B>(
    val request: A,
    val response: B
)

internal infix fun <A, B> A.toRP(that: B): RequestPair<A, B> = RequestPair(this, that)