@file:OptIn(ExperimentalObjCRefinement::class)

import androidx.compose.runtime.Composable
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

abstract class MoleculeViewModel<State, Effect, Event> : KMMViewModel() {
    private val events = MutableSharedFlow<Event>(extraBufferCapacity = 20)
    private val _effects = MutableSharedFlow<Effect>(extraBufferCapacity = 20)


    val state: StateFlow<State> by lazy(LazyThreadSafetyMode.NONE) {
        viewModelScope.coroutineScope.launchMolecule(mode = RecompositionMode.Immediate) {
            state(events) {
                _effects.tryEmit(it)
            }
        }.let {
            // This uses KMMViewModel's stateIn, which updates SwiftUI whenever this state changes
            it.stateIn(viewModelScope, SharingStarted.Lazily, it.value)
        }
    }
    val effects: Flow<Effect> = _effects
    fun eventSink(event: Event) {
        events.tryEmit(event)
    }

    @HiddenFromObjC
    @Composable
    protected abstract fun state(events: Flow<Event>, effectSink: (Effect) -> Unit): State
}
