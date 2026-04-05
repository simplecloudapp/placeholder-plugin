package app.simplecloud.placeholder.plugin.paper.executor

import app.simplecloud.api.CloudApi
import app.simplecloud.api.server.Server
import app.simplecloud.placeholder.plugin.paper.placeholder.Placeholder
import kotlinx.coroutines.future.await

/**
 * @author Niklas Nieberler
 */

class ServerPlaceholderExecutor : PlaceholderExecutor<Server>(
    { it.server().getServerById(System.getenv("SIMPLECLOUD_UNIQUE_ID")).await() }
) {

    override fun getPlaceholders(cloudApi: CloudApi) = listOf<Placeholder<Server>>(
        Placeholder("server_id") { it.serverId },
        Placeholder("server_type") { it.group.type },
        Placeholder("server_host") { it.serverhostId },
        Placeholder("server_numerical_id") { it.numericalId },
        Placeholder("server_ip") { it.ip },
        Placeholder("server_port") { it.port },
        Placeholder("server_max_players") { it.maxPlayers },
        Placeholder("server_min_memory") { it.minMemory },
        Placeholder("server_max_memory") { it.maxMemory },
        Placeholder("server_player_count") { it.playerCount },
        Placeholder("server_state") { it.state }
    )

}