package fixtures

import io.github.vp.lovelydi.interfaces.Blueprint
import io.github.vp.lovelydi.interfaces.Container
import io.github.vp.lovelydi.interfaces.CreateInstance
import io.github.vp.lovelydi.interfaces.OnDispose

fun <V> testBlueprint(
    onDispose: OnDispose<V>? = null,
    createInstance: CreateInstance<V>,
) = object : Blueprint<V> {
    override fun createValue(scope: Container): V = createInstance()

    override fun shouldUpdateValue(resolvedValue: V) = true

    override fun disposeValue(resolvedValue: V) {
        onDispose?.invoke(resolvedValue)
    }
}