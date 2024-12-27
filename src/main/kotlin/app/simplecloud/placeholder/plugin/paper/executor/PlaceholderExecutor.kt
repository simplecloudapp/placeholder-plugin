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

    abstract fun getPlaceholders(controllerApi: ControllerApi.Coroutine): List<Placeholder<T>>

    suspend fun executePlaceholder(key: String, controllerApi: ControllerApi.Coroutine): Any? {
        val placeholder = getPlaceholder(controllerApi, key) ?: return null
        val handle = this.controllerHandler.handle(controllerApi)
        return placeholder.invoke(handle)
    }

    private fun getPlaceholder(controllerApi: ControllerApi.Coroutine, key: String): Placeholder<T>? {
        return getPlaceholders(controllerApi).firstOrNull { it.key == key }
    }

    fun hasPlaceholder(controllerApi: ControllerApi.Coroutine, key: String): Boolean {
        return getPlaceholder(controllerApi, key) != null
    }

}