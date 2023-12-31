import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun <Original : Any> rememberSaveable(
    vararg inputs: Any?,
    saver: Saver<Original, out Any>?,
    key: String?,
    init: () -> Original
): Original = remember(init)
