package app.simplecloud.placeholder.plugin.paper.executor.handler

import app.simplecloud.api.CloudApi
import app.simplecloud.api.group.Group
import app.simplecloud.api.group.GroupApi
import app.simplecloud.api.persistentserver.PersistentServer
import app.simplecloud.api.persistentserver.PersistentServerApi
import app.simplecloud.api.server.Server
import app.simplecloud.api.server.ServerApi
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Proxy
import java.util.concurrent.CompletableFuture
import kotlin.test.Test
import kotlin.test.assertEquals

class PlaceholderExecutorsTest {

    @Test
    fun `resolves a targeted group by name`() = runBlocking {
        val group = proxy<Group> { method, _ ->
            when (method.name) {
                "getName" -> "Lobby"
                else -> null
            }
        }
        val groupApi = proxy<GroupApi> { method, arguments ->
            when (method.name) {
                "getGroupByName" -> {
                    assertEquals("Lobby", arguments.single())
                    CompletableFuture.completedFuture(group)
                }
                else -> null
            }
        }
        val cloudApi = cloudApi(groupApi = groupApi)

        assertEquals(
            "Lobby",
            PlaceholderExecutors.findPlaceholder("group_Lobby_name", cloudApi)
        )
    }

    @Test
    fun `resolves a targeted persistent server by name`() = runBlocking {
        val persistentServer = proxy<PersistentServer> { method, _ ->
            when (method.name) {
                "isActive" -> true
                else -> null
            }
        }
        val persistentServerApi = proxy<PersistentServerApi> { method, arguments ->
            when (method.name) {
                "getPersistentServerByName" -> {
                    assertEquals("MainLobby", arguments.single())
                    CompletableFuture.completedFuture(persistentServer)
                }
                else -> null
            }
        }
        val cloudApi = cloudApi(persistentServerApi = persistentServerApi)

        assertEquals(
            "true",
            PlaceholderExecutors.findPlaceholder(
                "persistent_server_MainLobby_is_active",
                cloudApi
            )
        )
    }

    @Test
    fun `resolves a targeted server by group and numerical id`() = runBlocking {
        val server = proxy<Server> { method, _ ->
            when (method.name) {
                "getPlayerCount" -> 12
                else -> null
            }
        }
        val serverApi = proxy<ServerApi> { method, arguments ->
            when (method.name) {
                "getServerByNumericalId" -> {
                    assertEquals("Lobby", arguments[0])
                    assertEquals(2, arguments[1])
                    CompletableFuture.completedFuture(server)
                }
                else -> null
            }
        }
        val cloudApi = cloudApi(serverApi = serverApi)

        assertEquals(
            "12",
            PlaceholderExecutors.findPlaceholder(
                "server_Lobby:2_player_count",
                cloudApi
            )
        )
    }

    @Test
    fun `resolves a targeted server by unique id`() = runBlocking {
        val server = proxy<Server> { method, _ ->
            when (method.name) {
                "getServerId" -> "server-123"
                else -> null
            }
        }
        val serverApi = proxy<ServerApi> { method, arguments ->
            when (method.name) {
                "getServerById" -> {
                    assertEquals("server-123", arguments.single())
                    CompletableFuture.completedFuture(server)
                }
                else -> null
            }
        }
        val cloudApi = cloudApi(serverApi = serverApi)

        assertEquals(
            "server-123",
            PlaceholderExecutors.findPlaceholder(
                "server_id:server-123_id",
                cloudApi
            )
        )
    }

    @Test
    fun `returns null when a targeted resource cannot be loaded`() = runBlocking {
        val groupApi = proxy<GroupApi> { method, _ ->
            when (method.name) {
                "getGroupByName" -> CompletableFuture.failedFuture<Group>(
                    NoSuchElementException("Unknown group")
                )
                else -> null
            }
        }
        val cloudApi = cloudApi(groupApi = groupApi)

        assertEquals(
            null,
            PlaceholderExecutors.findPlaceholder("group_Unknown_name", cloudApi)
        )
    }

    private fun cloudApi(
        serverApi: ServerApi? = null,
        persistentServerApi: PersistentServerApi? = null,
        groupApi: GroupApi? = null
    ): CloudApi {
        return proxy { method, _ ->
            when (method.name) {
                "server" -> serverApi
                "persistentServer" -> persistentServerApi
                "group" -> groupApi
                else -> null
            }
        }
    }

    private inline fun <reified T> proxy(
        crossinline handler: (java.lang.reflect.Method, Array<out Any?>) -> Any?
    ): T {
        return Proxy.newProxyInstance(
            T::class.java.classLoader,
            arrayOf(T::class.java)
        ) { _, method, arguments -> handler(method, arguments ?: emptyArray()) } as T
    }
}
