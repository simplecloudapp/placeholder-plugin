package app.simplecloud.placeholder.plugin.paper.executor.handler

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TargetedPlaceholderParserTest {

    @Test
    fun `parses a targeted group placeholder`() {
        assertEquals(
            TargetedPlaceholderRequest(
                TargetType.GROUP,
                "Lobby_EU",
                "group_player_count"
            ),
            TargetedPlaceholderParser.parse("group_Lobby_EU_player_count")
        )
    }

    @Test
    fun `parses a targeted persistent server placeholder`() {
        assertEquals(
            TargetedPlaceholderRequest(
                TargetType.PERSISTENT_SERVER,
                "Main_Lobby",
                "persistent_server_is_active"
            ),
            TargetedPlaceholderParser.parse("persistent_server_Main_Lobby_is_active")
        )
    }

    @Test
    fun `parses a server selected by group and numerical id`() {
        assertEquals(
            TargetedPlaceholderRequest(
                TargetType.SERVER,
                "Lobby_EU:2",
                "server_player_count"
            ),
            TargetedPlaceholderParser.parse("server_Lobby_EU:2_player_count")
        )
    }

    @Test
    fun `parses a server selected by unique id`() {
        assertEquals(
            TargetedPlaceholderRequest(
                TargetType.SERVER,
                "id:server-123",
                "server_state"
            ),
            TargetedPlaceholderParser.parse("server_id:server-123_state")
        )
    }

    @Test
    fun `does not interpret an existing placeholder as targeted`() {
        assertNull(TargetedPlaceholderParser.parse("server_player_count"))
        assertNull(TargetedPlaceholderParser.parse("group_name"))
        assertNull(TargetedPlaceholderParser.parse("persistent_server_is_active"))
    }

    @Test
    fun `rejects unknown properties and incomplete targets`() {
        assertNull(TargetedPlaceholderParser.parse("server_Lobby:1_unknown"))
        assertNull(TargetedPlaceholderParser.parse("server__state"))
        assertNull(TargetedPlaceholderParser.parse("group__player_count"))
    }
}
