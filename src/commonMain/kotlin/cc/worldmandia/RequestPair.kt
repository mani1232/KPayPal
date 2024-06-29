package cc.worldmandia

data class RequestPair<out A, out B>(
    val request: A,
    val response: B
)

infix fun <A, B> A.to(that: B): RequestPair<A, B> = RequestPair(this, that)