package app.simplecloud.placeholder.plugin.paper

import app.simplecloud.controller.api.ControllerApi
import app.simplecloud.controller.shared.group.Group
import app.simplecloud.controller.shared.server.Server
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Niklas Nieberler
 */

class PaperPlugin : JavaPlugin() {

    private val controllerApi = ControllerApi.createCoroutineApi()

    override fun onEnable() {

        CoroutineScope(Dispatchers.IO).launch {
            val server = getControllerServer()
            val group = getControllerGroup(server)
            SimpleCloudPlaceholderExpansion(server, group).register()
        }
    }

    private suspend fun getControllerServer(): Server {
       return this.controllerApi.getServers().getServerById(System.getenv("SIMPLECLOUD_UNIQUE_ID"))
    }

    private suspend fun getControllerGroup(server: Server): Group {
        return this.controllerApi.getGroups().getGroupByName(server.group)
    }

}