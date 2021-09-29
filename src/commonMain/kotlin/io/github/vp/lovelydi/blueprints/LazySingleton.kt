package io.github.vp.lovelydi.blueprints

import io.github.vp.lovelydi.interfaces.CreateInstance
import io.github.vp.lovelydi.interfaces.Blueprint
import io.github.vp.lovelydi.interfaces.Container
import io.github.vp.lovelydi.interfaces.OnDispose

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