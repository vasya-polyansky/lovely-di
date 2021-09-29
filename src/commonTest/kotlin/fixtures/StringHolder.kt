package fixtures

interface StringHolder {
    suspend fun getValue(): String
}

class TestStringHolder(private val value: String) : StringHolder {
    override suspend fun getValue() = value
}
