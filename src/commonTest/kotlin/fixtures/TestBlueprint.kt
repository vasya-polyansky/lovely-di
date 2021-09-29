package fixtures

import dev.vp.lovelydi.interfaces.Blueprint
import dev.vp.lovelydi.interfaces.Container
import dev.vp.lovelydi.interfaces.CreateInstance
import dev.vp.lovelydi.interfaces.OnDispose

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