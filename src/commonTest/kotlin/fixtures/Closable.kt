package fixtures

interface Closable {
    val isClosed: Boolean

    fun close()
}

class TestClosable : Closable {
    override var isClosed = false

    override fun close() {
        isClosed = true
    }
}
