package app.simplecloud.placeholder.plugin.paper.executor

import app.simplecloud.api.CloudApi
import app.simplecloud.api.group.Group
import app.simplecloud.api.server.ServerQuery
import app.simplecloud.placeholder.plugin.paper.placeholder.Placeholder
import kotlinx.coroutines.future.await

/**
 * @author Niklas Nieberler
 */

class GroupPlaceholderExecutor : PlaceholderExecutor<Group>(
    { it.group().getGroupByName(System.getenv("SIMPLECLOUD_GROUP")).await() }
) {

    override fun getPlaceholders(cloudApi: CloudApi): List<Placeholder<Group>> {
        return listOf(
            Placeholder("group_name") { it.name },
            Placeholder("group_type") { it.type },
            Placeholder("group_min_memory") { it.minMemory },
            Placeholder("group_max_memory") { it.maxMemory },
            Placeholder("group_max_players") { it.maxPlayers },
            Placeholder("group_player_count") {
                val query = ServerQuery.create()
                    .filterByServerGroupName(it.name)
                cloudApi.server().getAllServers(query).await().sumOf { it.playerCount }
            },
        )
    }

}