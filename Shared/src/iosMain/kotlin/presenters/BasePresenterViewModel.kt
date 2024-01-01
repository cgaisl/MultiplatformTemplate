package presenters

import Rendering
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

abstract class BasePresenterViewModel<State, Effect, Event> : KMMViewModel() {
    private val rendering: StateFlow<Rendering<State, Effect, Event>> by lazy(
        LazyThreadSafetyMode.NONE
    ) {
        viewModelScope.coroutineScope.presenter()
            .let {
                // This uses KMMViewModel's stateIn, which updates SwiftUI whenever the state changes
                it.stateIn(viewModelScope, SharingStarted.Lazily, it.value)
            }
    }

    protected abstract fun CoroutineScope.presenter(): StateFlow<Rendering<State, Effect, Event>>

    val state: State
        get() = rendering.value.state

    val effects: Flow<Effect>
        get() = rendering.value.effects

    val eventSink: (Event) -> Unit
        get() = rendering.value.eventSink
}
