package dev.vp.lovelydi.interfaces

/**
 * Simple synchronous blueprint defining how to create,
 * update and dispose a value produced by this blueprint.
 */
interface Blueprint<V> {
    /**
     * Creates a new value of this blueprint.
     * Used by [Container] to initialize or update a value.
     * [scope] is a current container.
     */
    fun createValue(scope: Container): V

    /**
     * Defines whether a value should be updated.
     * Used by [Container] to make choice whether to keep the old value or create a new one.
     */
    fun shouldUpdateValue(resolvedValue: V): Boolean

    /**
     * Disposes a value produced by this blueprint.
     */
    fun disposeValue(resolvedValue: V)
}
