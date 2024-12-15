package app.simplecloud.placeholder.plugin.paper.executor.handler

import app.simplecloud.controller.api.ControllerApi
import app.simplecloud.placeholder.plugin.paper.executor.GroupPlaceholderExecutor
import app.simplecloud.placeholder.plugin.paper.executor.PlaceholderExecutor
import app.simplecloud.placeholder.plugin.paper.executor.ServerPlaceholderExecutor

/**
 * @author Niklas Nieberler
 */

object PlaceholderExecutors {

    private val executors = listOf<PlaceholderExecutor<out Any>>(
        ServerPlaceholderExecutor(),
        GroupPlaceholderExecutor()
    )

    suspend fun findPlaceholder(key: String, controllerApi: ControllerApi.Coroutine): String? {
        val executor = this.executors.firstOrNull { it.hasPlaceholder(key) } ?: return null
        return executor.executePlaceholder(key, controllerApi).toString()
    }

}