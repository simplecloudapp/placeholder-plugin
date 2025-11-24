package app.simplecloud.placeholder.plugin.paper

import app.simplecloud.api.CloudApi
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import app.simplecloud.placeholder.plugin.paper.executor.handler.PlaceholderExecutors
import kotlinx.coroutines.runBlocking

/**
 * @author Niklas Nieberler
 */

class SimpleCloudPlaceholderExpansion(
    private val cloudApi: CloudApi
) : PlaceholderExpansion() {

    override fun getIdentifier() = "simplecloud"

    override fun getAuthor() = "MrManHD"

    override fun getVersion() = "1.1-SNAPSHOT"

    override fun canRegister() = true

    override fun persist() = true

    override fun onRequest(player: OfflinePlayer, params: String): String? {
        return runBlocking { PlaceholderExecutors.findPlaceholder(params, cloudApi) }
    }

}