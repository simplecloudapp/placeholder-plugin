package app.simplecloud.placeholder.plugin.paper.executor.handler

import app.simplecloud.api.CloudApi

/**
 * @author Niklas Nieberler
 */

fun interface PlaceholderControllerHandler<T> {

    suspend fun handle(cloudApi: CloudApi): T

}