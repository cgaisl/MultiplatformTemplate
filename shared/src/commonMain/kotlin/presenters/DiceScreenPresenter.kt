package presenters

import Rendering
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableSharedFlow
import rememberSaveable

data class DiceScreenState(
    val currentDice: Int,
)

sealed interface DiceScreenEvent {
    data object RollDicePressed : DiceScreenEvent
}

sealed interface DiceScreenEffect {
    data object DiceRolled : DiceScreenEffect
}

@Composable
fun diceScreenPresenter(
): Rendering<DiceScreenState, DiceScreenEffect, DiceScreenEvent> {
    val events = remember { MutableSharedFlow<DiceScreenEvent>(extraBufferCapacity = 20) }
    val effects = remember { MutableSharedFlow<DiceScreenEffect>(extraBufferCapacity = 20) }
    var currentDice by rememberSaveable { mutableIntStateOf(1) }

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is DiceScreenEvent.RollDicePressed -> {
                    currentDice = (1..6).random()
                    effects.tryEmit(DiceScreenEffect.DiceRolled)
                }
            }
        }
    }

    return Rendering(
        state = DiceScreenState(currentDice),
        effects = effects,
        eventSink = { events.tryEmit(it) }
    )
}
