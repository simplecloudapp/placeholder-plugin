package app.simplecloud.placeholder.plugin.paper.placeholder

/**
 * @author Niklas Nieberler
 */

fun interface PlaceholderHandler<T> {

    suspend fun handle(value: T): Any?

}