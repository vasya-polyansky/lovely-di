package dev.vp.lovelydi.blueprints

import dev.vp.lovelydi.interfaces.CreateInstance
import dev.vp.lovelydi.interfaces.Blueprint
import dev.vp.lovelydi.interfaces.Container
import dev.vp.lovelydi.interfaces.OnDispose

/**
 * Factory blueprint.
 * Its value will be created each time [Container.get] is called.
 */
private class Factory<V>(
    private val onDispose: OnDispose<V>? = null,
    private val createInstance: CreateInstance<V>,
) : Blueprint<V> {
    override fun disposeValue(resolvedValue: V) {
        onDispose?.invoke(resolvedValue)
    }

    override fun createValue(scope: Container) = createInstance()

    override fun shouldUpdateValue(resolvedValue: V) = true
}

fun <T> factory(
    onDispose: OnDispose<T>? = null,
    createInstance: CreateInstance<T>,
): Blueprint<T> = Factory(onDispose, createInstance)