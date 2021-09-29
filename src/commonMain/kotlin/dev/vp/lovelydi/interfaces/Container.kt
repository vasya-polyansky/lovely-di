package dev.vp.lovelydi.interfaces

/**
 * framework.dev.vp.lovelydi.containers.Container to manage dev.vp.lovelydi.blueprints.
 */
interface Container {
    /**
     * Resolves a value produced by [blueprint].
     */
    fun <V : Any> get(blueprint: Blueprint<V>): V

    /**
     * Disposes [resolvedValue] by [blueprint]'s disposing rule.
     *
     * Throws [DisposingException] if something goes wrong.
     */
    fun <V : Any> dispose(blueprint: Blueprint<V>, resolvedValue: V)
}

fun <C : Container, V : Any> C.get(block: C.() -> Blueprint<V>): V = get(block())

fun <C : Container, V : Any> C.inject(block: C.() -> Blueprint<V>): Lazy<V> = lazy { get(block()) }

fun <C : Container, V : Any> C.dispose(
    resolvedValue: V,
    block: C.() -> Blueprint<V>,
) = dispose(block(), resolvedValue)
