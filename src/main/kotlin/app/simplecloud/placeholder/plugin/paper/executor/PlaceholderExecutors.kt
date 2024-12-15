package app.simplecloud.placeholder.plugin.paper.executor

import app.simplecloud.controller.shared.group.Group
import app.simplecloud.controller.shared.server.Server

/**
 * @author Niklas Nieberler
 */

object PlaceholderExecutors {

    private val executors = listOf<PlaceholderExecutor>(
        ServerPlaceholderExecutor(),
        GroupPlaceholderExecutor()
    )

    fun findPlaceholder(key: String, server: Server, group: Group): String? {
        val placeholderHandler = this.executors
            .map { it.getPlaceholders() }
            .flatten()
            .firstOrNull { it.key.equals(key, true) }?.handler
        return placeholderHandler?.handle(server, group).toString()
    }

}