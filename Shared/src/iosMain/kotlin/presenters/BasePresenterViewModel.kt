package presenters

import Rendering
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

abstract class BasePresenterViewModel<State, Effect, Event> : KMMViewModel() {
    private val rendering: StateFlow<Rendering<State, Effect, Event>> by lazy(
        LazyThreadSafetyMode.NONE
    ) {
        viewModelScope.coroutineScope.presenter()
            .let {
                it.stateIn(viewModelScope, SharingStarted.Lazily, it.value)
            }
    }

    protected abstract fun CoroutineScope.presenter(): StateFlow<Rendering<State, Effect, Event>>

    val renderingValue: Rendering<State, Effect, Event>
        get() = rendering.value
}
