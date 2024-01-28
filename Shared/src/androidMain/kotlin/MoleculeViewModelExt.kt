import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow

// convenience function to be used with Kotlin destructuring
// i.e.: val (state, effects, eventSink) = viewModel.rendering()
@Composable
fun <State, Effect, Event> MoleculeViewModel<State, Effect, Event>.rendering(): Triple<State, Flow<Effect>, (Event) -> Unit> {
    return Triple(state.collectAsState().value, effects) { eventSink(it) }
}
