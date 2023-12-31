import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.rememberSaveable as androidRememberSaveable

@Suppress("UNCHECKED_CAST")
@Composable
actual fun <Original : Any> rememberSaveable(
    vararg inputs: Any?,
    saver: Saver<Original, out Any>?,
    key: String?,
    init: () -> Original
): Original = androidRememberSaveable(
    inputs = inputs,
    saver = saver?.let { customSaver ->
        object : androidx.compose.runtime.saveable.Saver<Any, Any> {
            override fun restore(value: Any): Original? =
                customSaver.restore(value)

            override fun SaverScope.save(value: Any): Any? = customSaver.save(value as Original)
        } as androidx.compose.runtime.saveable.Saver<Original, out Any>
    } ?: autoSaver(),
    key = key,
    init = init
)
