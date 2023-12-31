import app.cash.molecule.AndroidUiDispatcher
import kotlin.coroutines.CoroutineContext

actual val contextWithRecompositionClock: CoroutineContext
    get() = AndroidUiDispatcher.Main
