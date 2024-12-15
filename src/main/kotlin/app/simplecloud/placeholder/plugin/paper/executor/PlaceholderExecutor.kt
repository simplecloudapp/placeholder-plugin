package app.simplecloud.placeholder.plugin.paper.executor

import app.simplecloud.controller.api.ControllerApi
import app.simplecloud.placeholder.plugin.paper.executor.handler.PlaceholderControllerHandler
import app.simplecloud.placeholder.plugin.paper.placeholder.Placeholder

/**
 * @author Niklas Nieberler
 */

abstract class PlaceholderExecutor<T>(
    private val controllerHandler: PlaceholderControllerHandler<T>
) {

    abstract fun getPlaceholders(): List<Placeholder<T>>

    suspend fun executePlaceholder(key: String, controllerApi: ControllerApi.Coroutine): Any? {
        val placeholder = getPlaceholder(key) ?: return null
        val handle = this.controllerHandler.handle(controllerApi)
        return placeholder.invoke(handle)
    }

    fun getPlaceholder(key: String): Placeholder<T>? {
        return getPlaceholders().firstOrNull { it.key == key }
    }

    fun hasPlaceholder(key: String): Boolean {
        return getPlaceholder(key) != null
    }

}