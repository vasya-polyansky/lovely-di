package io.github.vp.lovelydi.containers.simple

import io.github.vp.lovelydi.interfaces.Blueprint

/**
 *  Just a wrapper on [Map] to store blueprint values in an easier way.
 */
class ResolvedValueStore {
    private val savedValues = mutableMapOf<Blueprint<*>, Any>()

    fun isValueSaved(blueprint: Blueprint<*>): Boolean = savedValues.containsKey(blueprint)

    fun <V : Any> saveValueForBlueprint(blueprint: Blueprint<V>, value: V) {
        savedValues[blueprint] = value
    }

    // using reference equality
    @Suppress("UNCHECKED_CAST")
    fun <V> geValueForBlueprint(blueprint: Blueprint<V>): V =
        savedValues.entries.first { it.key === blueprint }.value as V

    fun removeBlueprintAndValue(blueprint: Blueprint<*>) {
        savedValues.remove(blueprint)
    }
}
