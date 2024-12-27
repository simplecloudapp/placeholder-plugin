package app.simplecloud.placeholder.plugin.paper.placeholder

/**
 * @author Niklas Nieberler
 */

data class Placeholder<T>(
    val key: String,
    private val handler: PlaceholderHandler<T>
) {

    suspend fun invoke(value: T): Any? {
        return this.handler.handle(value)
    }

}