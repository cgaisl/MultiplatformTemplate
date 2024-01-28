package viewModels

import MoleculeViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.Flow

data class DiceScreenState(
    val currentDice: Int,
)

sealed interface DiceScreenEvent {
    data object RollDicePressed : DiceScreenEvent
}

sealed interface DiceScreenEffect {
    data object DiceRolled : DiceScreenEffect
}


class DiceScreenViewModel : MoleculeViewModel<DiceScreenState, DiceScreenEffect, DiceScreenEvent>() {
    @Composable
    override fun state(events: Flow<DiceScreenEvent>, effectSink: (DiceScreenEffect) -> Unit): DiceScreenState {
        var currentDice by remember { mutableIntStateOf(1) }

        LaunchedEffect(Unit) {
            events.collect { event ->
                when (event) {
                    is DiceScreenEvent.RollDicePressed -> {
                        currentDice = (1..6).random()
                        effectSink(DiceScreenEffect.DiceRolled)
                    }
                }
            }
        }

        return DiceScreenState(currentDice)
    }

}
