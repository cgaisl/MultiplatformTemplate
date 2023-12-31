import app.cash.molecule.DisplayLinkClock
import kotlin.coroutines.CoroutineContext

actual val contextWithRecompositionClock: CoroutineContext
    get() = DisplayLinkClock
