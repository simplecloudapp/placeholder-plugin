package app.simplecloud.placeholder.plugin.paper

import app.simplecloud.controller.api.ControllerApi
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Niklas Nieberler
 */

class PaperPlugin : JavaPlugin() {

    override fun onEnable() {
        val controllerApi = ControllerApi.createCoroutineApi()
        SimpleCloudPlaceholderExpansion(controllerApi).register()
    }
}