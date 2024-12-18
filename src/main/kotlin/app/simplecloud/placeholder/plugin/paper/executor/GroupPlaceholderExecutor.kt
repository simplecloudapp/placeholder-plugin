package app.simplecloud.placeholder.plugin.paper.executor

import app.simplecloud.controller.api.ControllerApi
import app.simplecloud.controller.shared.group.Group
import app.simplecloud.placeholder.plugin.paper.placeholder.Placeholder

/**
 * @author Niklas Nieberler
 */

class GroupPlaceholderExecutor : PlaceholderExecutor<Group>(
    { it.getGroups().getGroupByName(System.getenv("SIMPLECLOUD_GROUP")) }
) {

    override fun getPlaceholders(controllerApi: ControllerApi.Coroutine) = listOf<Placeholder<Group>>(
        Placeholder("group_name") { it.name },
        Placeholder("group_type") { it.type },
        Placeholder("group_min_memory") { it.minMemory },
        Placeholder("group_max_memory") { it.maxMemory },
        Placeholder("group_max_players") { it.maxPlayers },
        Placeholder("group_player_count") { controllerApi.getServers().getServersByGroup(it.name).sumOf { it.playerCount } },
    )

}