package test

import io.github.vp.lovelydi.containers.simple.SimpleContainer
import io.github.vp.lovelydi.exceptions.DisposingException
import io.github.vp.lovelydi.interfaces.*
import fixtures.*
import fixtures.StringHolder
import fixtures.TestStringHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SimpleContainerTest {
    companion object {
        private const val testValue = "TEST_VALUE"
    }

    private class TestContainer : SimpleContainer() {
        val stringHolder = testBlueprint<StringHolder> { TestStringHolder(testValue) }
        val closable = testBlueprint<Closable>(onDispose = { it.close() }) { TestClosable() }
    }

    private val di = TestContainer()


    @Test
    fun `Value resolving`() = runBlockingTest {
        val holder = di.get(di.stringHolder)
        // TODO: Check if Blueprint's shouldUpdate is called
        // TODO: Check if Blueprint's create is called
        assertEquals(testValue, holder.getValue())
    }

    @Test
    fun `Value disposing`() {
        val closableInstance = di.get { closable }
        di.dispose(closableInstance) { closable }

        // TODO: Check if Blueprint's dispose is called
        assertTrue { closableInstance.isClosed }
    }

    @Test
    fun `Disposing exception`() {
        val fakeInstance = TestClosable()
        assertFailsWith(DisposingException::class) {
            di.dispose(fakeInstance) { closable }
        }
    }

    @Test
    fun `Container context`() {
        val holder = di.get {
            assertIs<TestContainer>(this@get)
            di.stringHolder
        }

        di.inject {
            assertIs<TestContainer>(this@inject)
            di.stringHolder
        }

        di.dispose(holder) {
            assertIs<TestContainer>(this@dispose)
            di.stringHolder
        }
    }
}