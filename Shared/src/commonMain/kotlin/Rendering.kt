import androidx.compose.runtime.Composable
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

data class Rendering<State, Effect, Event>(
    val state: State,
    val effects: Flow<Effect>,
    val eventSink: (Event) -> Unit
)

fun <State, Effect, Event> getRendering(
    scope: CoroutineScope,
    presenter: @Composable () -> Rendering<State, Effect, Event>,
): StateFlow<Rendering<State, Effect, Event>> {
    return scope.launchMolecule(RecompositionMode.ContextClock) {
        presenter()
    }
}
