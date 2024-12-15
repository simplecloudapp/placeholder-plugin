package app.simplecloud.placeholder.plugin.paper.executor.handler

import app.simplecloud.controller.api.ControllerApi

/**
 * @author Niklas Nieberler
 */

fun interface PlaceholderControllerHandler<T> {

    suspend fun handle(controllerApi: ControllerApi.Coroutine): T

}