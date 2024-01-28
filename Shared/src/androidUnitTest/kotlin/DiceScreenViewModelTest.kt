import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import utils.MainDispatcherRule
import viewModels.DiceScreenEffect
import viewModels.DiceScreenEvent
import viewModels.DiceScreenViewModel
import kotlin.test.assertEquals

class DiceScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when RollDicePressed event is sent, DiceRolled effect is emitted`() = runTest {
        val viewModel = DiceScreenViewModel()

        viewModel.state.test {
            assertEquals(1, awaitItem().currentDice)

            viewModel.effects.test {
                viewModel.eventSink(DiceScreenEvent.RollDicePressed)
                assertEquals(DiceScreenEffect.DiceRolled, awaitItem())
            }

            cancelAndConsumeRemainingEvents()
        }
    }
}
