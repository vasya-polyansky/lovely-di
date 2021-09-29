package dev.vp.lovelydi.containers.simple

import dev.vp.lovelydi.exceptions.DisposingException
import dev.vp.lovelydi.interfaces.Blueprint
import dev.vp.lovelydi.interfaces.Container

/**
 * Container to manage dev.vp.lovelydi.blueprints.
 * Stores resolved values for reuse in case a blueprint requires it.
 */
abstract class SimpleContainer : Container {
    private val lastResolvedValues = ResolvedValueStore()

    private fun <V : Any> createAndSaveValue(blueprint: Blueprint<V>): V {
        val value = blueprint.createValue(this)
        lastResolvedValues.saveValueForBlueprint(blueprint, value)
        return value
    }

    override fun <V : Any> get(blueprint: Blueprint<V>): V {
        if (!lastResolvedValues.isValueSaved(blueprint as Blueprint<*>)) {
            return createAndSaveValue(blueprint)
        }

        val lastValue = lastResolvedValues.geValueForBlueprint(blueprint)
        if (blueprint.shouldUpdateValue(lastValue)) {
            return createAndSaveValue(blueprint)
        }

        return lastValue
    }

    override fun <T : Any> dispose(blueprint: Blueprint<T>, resolvedValue: T) {
        if (!lastResolvedValues.isValueSaved(blueprint)) {
            throw DisposingException("You are trying to dispose a value before it has been resolved.")
        }
        blueprint.disposeValue(resolvedValue)
        lastResolvedValues.removeBlueprintAndValue(blueprint)
    }
}