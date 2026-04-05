package app.simplecloud.placeholder.plugin.paper

import app.simplecloud.api.CloudApi
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Niklas Nieberler
 */

class PaperPlugin : JavaPlugin() {

    override fun onEnable() {
        val cloudApi = CloudApi.create()
        SimpleCloudPlaceholderExpansion(cloudApi).register()
    }
}