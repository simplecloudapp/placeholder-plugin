package app.simplecloud.placeholder.plugin.paper.executor

import app.simplecloud.api.CloudApi
import app.simplecloud.placeholder.plugin.paper.executor.handler.PlaceholderControllerHandler
import app.simplecloud.placeholder.plugin.paper.placeholder.Placeholder

/**
 * @author Niklas Nieberler
 */

abstract class PlaceholderExecutor<T>(
    private val controllerHandler: PlaceholderControllerHandler<T>
) {

    abstract fun getPlaceholders(cloudApi: CloudApi): List<Placeholder<T>>

    suspend fun executePlaceholder(key: String, cloudApi: CloudApi): Any? {
        val handle = this.controllerHandler.handle(cloudApi)
        return executePlaceholder(key, cloudApi, handle)
    }

    suspend fun executePlaceholder(key: String, cloudApi: CloudApi, handle: T): Any? {
        val placeholder = getPlaceholder(cloudApi, key) ?: return null
        return placeholder.invoke(handle)
    }

    private fun getPlaceholder(cloudApi: CloudApi, key: String): Placeholder<T>? {
        return getPlaceholders(cloudApi).firstOrNull { it.key == key }
    }

    fun hasPlaceholder(cloudApi: CloudApi, key: String): Boolean {
        return getPlaceholder(cloudApi, key) != null
    }

}
