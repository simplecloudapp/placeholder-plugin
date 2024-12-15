package app.simplecloud.placeholder.plugin.paper.placeholder

import app.simplecloud.controller.shared.group.Group
import app.simplecloud.controller.shared.server.Server

/**
 * @author Niklas Nieberler
 */

fun interface PlaceholderHandler {

    fun handle(server: Server, group: Group): Any?

}