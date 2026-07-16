package app.simplecloud.placeholder.plugin.paper.executor.handler

import app.simplecloud.api.CloudApi
import app.simplecloud.placeholder.plugin.paper.executor.GroupPlaceholderExecutor
import app.simplecloud.placeholder.plugin.paper.executor.PersistentServerPlaceholderExecutor
import app.simplecloud.placeholder.plugin.paper.executor.ServerPlaceholderExecutor
import kotlinx.coroutines.future.await

/**
 * @author Niklas Nieberler
 */

object PlaceholderExecutors {

    private val serverExecutor = ServerPlaceholderExecutor()
    private val persistentServerExecutor = PersistentServerPlaceholderExecutor()
    private val groupExecutor = GroupPlaceholderExecutor()

    private val executors = listOf(
        serverExecutor,
        persistentServerExecutor,
        groupExecutor
    )

    suspend fun findPlaceholder(key: String, cloudApi: CloudApi): String? {
        return try {
            val executor = this.executors.firstOrNull { it.hasPlaceholder(cloudApi, key) }
            if (executor != null) {
                return executor.executePlaceholder(key, cloudApi)?.toString()
            }

            executeTargetedPlaceholder(key, cloudApi)?.toString()
        } catch (_: Exception) {
            null
        }
    }

    private suspend fun executeTargetedPlaceholder(key: String, cloudApi: CloudApi): Any? {
        val request = TargetedPlaceholderParser.parse(key) ?: return null

        return when (request.type) {
            TargetType.SERVER -> {
                val server = if (request.target.startsWith("id:")) {
                    val serverId = request.target.removePrefix("id:")
                    if (serverId.isBlank()) return null
                    cloudApi.server().getServerById(serverId).await()
                } else {
                    val separatorIndex = request.target.lastIndexOf(':')
                    if (separatorIndex <= 0) return null

                    val groupName = request.target.substring(0, separatorIndex)
                    val numericalId = request.target.substring(separatorIndex + 1).toIntOrNull() ?: return null
                    cloudApi.server().getServerByNumericalId(groupName, numericalId).await()
                }
                serverExecutor.executePlaceholder(request.placeholderKey, cloudApi, server)
            }

            TargetType.PERSISTENT_SERVER -> {
                val persistentServer = cloudApi.persistentServer()
                    .getPersistentServerByName(request.target)
                    .await()
                persistentServerExecutor.executePlaceholder(request.placeholderKey, cloudApi, persistentServer)
            }

            TargetType.GROUP -> {
                val group = cloudApi.group().getGroupByName(request.target).await()
                groupExecutor.executePlaceholder(request.placeholderKey, cloudApi, group)
            }
        }
    }

}
