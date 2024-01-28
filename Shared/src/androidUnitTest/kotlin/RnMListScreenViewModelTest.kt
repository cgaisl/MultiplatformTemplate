import app.cash.turbine.test
import data.RickAndMortyRepository
import data.RnMCharacter
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import utils.MainDispatcherRule
import viewModels.RnMListScreenEvent.CharacterClicked
import viewModels.RnMListScreenViewModel
import viewModels.RnmListScreenEffect.NavigateToDetail
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class RnMListScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @BeforeTest
    fun setup() {
        val mockRepo = mockk<RickAndMortyRepository> {
            coEvery { reloadCharactersFromNetwork() } just runs
            every { characters } returns MutableStateFlow(
                listOf(
                    RnMCharacter(
                        "1",
                        "Rick Sanchez",
                        "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                        "origin",
                        "species",
                        "gender",
                        "status",
                    )
                )
            )
        }
        startKoin { modules(module { single { mockRepo } }) }
    }

    @Test
    fun `characters are loaded and when character clicked, event is emitted`() = runTest {

        val viewModel = RnMListScreenViewModel()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(1, state.characters.size)

            viewModel.effects.test {
                viewModel.eventSink(CharacterClicked(state.characters.first()))
                assertEquals(
                    state.characters.first().id,
                    (awaitItem() as NavigateToDetail).characterId
                )
            }
        }
    }
}
