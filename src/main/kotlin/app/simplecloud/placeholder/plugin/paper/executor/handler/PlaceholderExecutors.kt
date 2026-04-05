package app.simplecloud.placeholder.plugin.paper.executor.handler

import app.simplecloud.api.CloudApi
import app.simplecloud.placeholder.plugin.paper.executor.GroupPlaceholderExecutor
import app.simplecloud.placeholder.plugin.paper.executor.PersistentServerPlaceholderExecutor
import app.simplecloud.placeholder.plugin.paper.executor.ServerPlaceholderExecutor

/**
 * @author Niklas Nieberler
 */

object PlaceholderExecutors {

    private val executors = listOf(
        ServerPlaceholderExecutor(),
        PersistentServerPlaceholderExecutor(),
        GroupPlaceholderExecutor()
    )

    suspend fun findPlaceholder(key: String, cloudApi: CloudApi): String? {
        val executor = this.executors.firstOrNull { it.hasPlaceholder(cloudApi, key) } ?: return null
        return executor.executePlaceholder(key, cloudApi).toString()
    }

}