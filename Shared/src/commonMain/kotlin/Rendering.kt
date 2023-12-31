import kotlinx.coroutines.flow.Flow

data class Rendering<State, Effect, Event>(
    val state: State,
    val effects: Flow<Effect>,
    val eventSink: (Event) -> Unit
)
