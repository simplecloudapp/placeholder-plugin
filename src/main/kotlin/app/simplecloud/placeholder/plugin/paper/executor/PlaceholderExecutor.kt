package app.simplecloud.placeholder.plugin.paper.executor

import app.simplecloud.placeholder.plugin.paper.placeholder.Placeholder

/**
 * @author Niklas Nieberler
 */

interface PlaceholderExecutor {

    fun getPlaceholders(): List<Placeholder>

}