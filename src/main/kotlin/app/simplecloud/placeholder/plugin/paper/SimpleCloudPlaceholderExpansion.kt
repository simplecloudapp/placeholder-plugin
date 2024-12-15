package app.simplecloud.placeholder.plugin.paper

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import app.simplecloud.controller.shared.group.Group
import app.simplecloud.controller.shared.server.Server
import app.simplecloud.placeholder.plugin.paper.executor.PlaceholderExecutors

/**
 * @author Niklas Nieberler
 */

class SimpleCloudPlaceholderExpansion(
    private val server: Server,
    private val group: Group
) : PlaceholderExpansion() {

    override fun getIdentifier() = "simplecloud-placeholder"

    override fun getAuthor() = "MrManHD"

    override fun getVersion() = "1.0-SNAPSHOT"

    override fun onRequest(player: OfflinePlayer, params: String): String? {
        return PlaceholderExecutors.findPlaceholder(params, this.server, this.group)
    }

}