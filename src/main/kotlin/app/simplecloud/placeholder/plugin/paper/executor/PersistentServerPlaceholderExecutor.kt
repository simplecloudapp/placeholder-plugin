package app.simplecloud.placeholder.plugin.paper.executor

import app.simplecloud.api.CloudApi
import app.simplecloud.api.persistentserver.PersistentServer
import app.simplecloud.placeholder.plugin.paper.placeholder.Placeholder
import kotlinx.coroutines.future.await

/**
 * @author Niklas Nieberler
 */

class PersistentServerPlaceholderExecutor : PlaceholderExecutor<PersistentServer>(
    { it.persistentServer().getPersistentServerById(System.getenv("SIMPLECLOUD_UNIQUE_ID")).await() }
) {

    override fun getPlaceholders(cloudApi: CloudApi) = listOf<Placeholder<PersistentServer>>(
        Placeholder("persistent_server_id") { it.persistentServerId },
        Placeholder("persistent_server_name") { it.name },
        Placeholder("persistent_server_type") { it.type },
        Placeholder("persistent_server_host") { it.serverhostId },
        Placeholder("persistent_server_max_players") { it.maxPlayers },
        Placeholder("persistent_server_min_memory") { it.minMemory },
        Placeholder("persistent_server_max_memory") { it.maxMemory },
        Placeholder("persistent_server_player_count") { it.playerCount },
        Placeholder("persistent_server_is_active") { it.isActive }
    )

}