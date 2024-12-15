package app.simplecloud.placeholder.plugin.paper.executor

import app.simplecloud.placeholder.plugin.paper.placeholder.Placeholder

/**
 * @author Niklas Nieberler
 */

class GroupPlaceholderExecutor : PlaceholderExecutor {

    override fun getPlaceholders() = listOf(
        Placeholder("group_name") { _, group -> group.name },
        Placeholder("group_type") { _, group -> group.type },
        Placeholder("group_min_memory") { _, group -> group.minMemory },
        Placeholder("group_max_memory") { _, group -> group.maxMemory },
        Placeholder("group_max_players") { _, group -> group.maxPlayers },
    )

}