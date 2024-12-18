package app.simplecloud.placeholder.plugin.paper.executor

import app.simplecloud.controller.shared.server.Server
import app.simplecloud.placeholder.plugin.paper.placeholder.Placeholder

/**
 * @author Niklas Nieberler
 */

class ServerPlaceholderExecutor : PlaceholderExecutor<Server>(
    { it.getServers().getServerById(System.getenv("SIMPLECLOUD_UNIQUE_ID")) }
) {

    override fun getPlaceholders() = listOf<Placeholder<Server>>(
        Placeholder("server_id") { it.uniqueId },
        Placeholder("server_type") { it.type },
        Placeholder("server_host") { it.host },
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