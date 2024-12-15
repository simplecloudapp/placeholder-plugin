package app.simplecloud.placeholder.plugin.paper.placeholder

/**
 * @author Niklas Nieberler
 */

fun interface PlaceholderHandler<T> {

    fun handle(value: T): Any?

}