import androidx.compose.runtime.Composable

@Composable
expect fun <Original : Any> rememberSaveable(
    vararg inputs: Any?,
    saver: Saver<Original, out Any>? = null,
    key: String? = null,
    init: () -> Original
): Original

interface Saver<Original, Saveable : Any> {
    /**
     * Convert the value into a saveable one. If null is returned the value will not be saved.
     */
    fun save(value: Original): Saveable?

    /**
     * Convert the restored value back to the original Class. If null is returned the value will
     * not be restored and would be initialized again instead.
     */
    fun restore(value: Any): Original?
}
