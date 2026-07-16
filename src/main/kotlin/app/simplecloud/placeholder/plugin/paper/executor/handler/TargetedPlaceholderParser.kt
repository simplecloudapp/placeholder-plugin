package app.simplecloud.placeholder.plugin.paper.executor.handler

/**
 * Parses placeholders that explicitly select a SimpleCloud resource.
 *
 * Examples:
 * - group_Lobby_player_count
 * - persistent_server_Lobby_is_active
 * - server_Lobby:1_state
 */
object TargetedPlaceholderParser {

    private val definitions = listOf(
        TargetDefinition(
            TargetType.PERSISTENT_SERVER,
            "persistent_server_",
            "persistent_server_",
            setOf(
                "id",
                "name",
                "type",
                "host",
                "max_players",
                "min_memory",
                "max_memory",
                "player_count",
                "is_active"
            )
        ),
        TargetDefinition(
            TargetType.SERVER,
            "server_",
            "server_",
            setOf(
                "id",
                "type",
                "host",
                "numerical_id",
                "ip",
                "port",
                "max_players",
                "min_memory",
                "max_memory",
                "player_count",
                "state"
            )
        ),
        TargetDefinition(
            TargetType.GROUP,
            "group_",
            "group_",
            setOf(
                "name",
                "type",
                "min_memory",
                "max_memory",
                "max_players",
                "player_count"
            )
        )
    )

    fun parse(key: String): TargetedPlaceholderRequest? {
        for (definition in definitions) {
            if (!key.startsWith(definition.inputPrefix)) {
                continue
            }

            val input = key.removePrefix(definition.inputPrefix)
            val property = definition.properties
                .sortedByDescending { it.length }
                .firstOrNull { input.endsWith("_$it") }
                ?: continue
            val target = input.removeSuffix("_$property")

            if (target.isBlank()) {
                continue
            }

            return TargetedPlaceholderRequest(
                definition.type,
                target,
                definition.placeholderPrefix + property
            )
        }

        return null
    }

    private data class TargetDefinition(
        val type: TargetType,
        val inputPrefix: String,
        val placeholderPrefix: String,
        val properties: Set<String>
    )
}

data class TargetedPlaceholderRequest(
    val type: TargetType,
    val target: String,
    val placeholderKey: String
)

enum class TargetType {
    SERVER,
    PERSISTENT_SERVER,
    GROUP
}
