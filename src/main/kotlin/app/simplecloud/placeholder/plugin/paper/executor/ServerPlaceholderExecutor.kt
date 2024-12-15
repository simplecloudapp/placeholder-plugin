package app.simplecloud.placeholder.plugin.paper.executor

import app.simplecloud.placeholder.plugin.paper.placeholder.Placeholder

/**
 * @author Niklas Nieberler
 */

class ServerPlaceholderExecutor : PlaceholderExecutor {

    override fun getPlaceholders() = listOf(
        Placeholder("server_id") { server, _ -> server.uniqueId },
        Placeholder("server_type") { server, _ -> server.type },
        Placeholder("server_host") { server, _ -> server.host },
        Placeholder("server_numerical_id") { server, _ -> server.numericalId },
        Placeholder("server_ip") { server, _ -> server.ip },
        Placeholder("server_port") { server, _ -> server.port },
        Placeholder("server_min_players") { server, _ -> server.minMemory },
        Placeholder("server_max_players") { server, _ -> server.maxPlayers },
        Placeholder("server_max_memory") { server, _ -> server.maxMemory },
        Placeholder("server_player_count") { server, _ -> server.playerCount },
        Placeholder("server_state") { server, _ -> server.state }
    )

}