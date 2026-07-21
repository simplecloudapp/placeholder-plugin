package app.simplecloud.placeholder.plugin.paper

import app.simplecloud.api.CloudApi
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Niklas Nieberler
 */

class PaperPlugin : JavaPlugin() {

    private lateinit var cloudApi: CloudApi

    override fun onEnable() {
        cloudApi = CloudApi.create()
        SimpleCloudPlaceholderExpansion(cloudApi, pluginMeta.version).register()
    }

    override fun onDisable() {
        if (::cloudApi.isInitialized) {
            cloudApi.close()
        }
    }
}
