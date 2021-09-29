package dev.vp.lovelydi.blueprints

import dev.vp.lovelydi.interfaces.CreateInstance
import dev.vp.lovelydi.interfaces.Blueprint
import dev.vp.lovelydi.interfaces.Container
import dev.vp.lovelydi.interfaces.OnDispose

/**
 * Lazy singleton blueprint.
 * Its value is created the first time [Container.get] is called and then reused.
 */
private class LazySingleton<T>(
    private val onDispose: OnDispose<T>? = null,
    private val createInstance: CreateInstance<T>,
) : Blueprint<T> {
    override fun shouldUpdateValue(resolvedValue: T) = false

    override fun disposeValue(resolvedValue: T) {
        onDispose?.invoke(resolvedValue)
    }

    override fun createValue(scope: Container) = createInstance()
}

fun <T> lazySingleton(
    onDispose: OnDispose<T>? = null,
    createInstance: CreateInstance<T>,
): Blueprint<T> = LazySingleton(
    onDispose = onDispose,
    createInstance = createInstance
)