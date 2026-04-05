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
        Placeholder("server_id") { it.persistentServerId },
        Placeholder("server_type") { it.type },
        Placeholder("server_host") { it.serverhostId },
        Placeholder("server_max_players") { it.maxPlayers },
        Placeholder("server_min_memory") { it.minMemory },
        Placeholder("server_max_memory") { it.maxMemory },
        Placeholder("server_player_count") { it.playerCount },
        Placeholder("server_is_active") { it.isActive }
    )

}