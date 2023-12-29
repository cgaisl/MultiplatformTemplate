import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.Test
import presenters.DiceScreenEffect
import presenters.DiceScreenEvent
import presenters.diceScreenPresenter
import kotlin.test.assertEquals

class DiceScreenPresenterTest {

    @Test
    fun `when RollDicePressed event is sent, DiceRolled effect is emitted`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            presenters.diceScreenPresenter()
        }.test {
            val (state, effects, eventSink) = awaitItem()
            assertEquals(1, state.currentDice)
            effects.onEach {
                println(it)
            }.test {
                eventSink(presenters.DiceScreenEvent.RollDicePressed)
                assertEquals(presenters.DiceScreenEffect.DiceRolled, awaitItem())
            }

            assert(awaitItem().state.currentDice in 1..6)
        }
    }
}
