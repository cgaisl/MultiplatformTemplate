import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import data.RickAndMortyRepository
import data.RnMCharacter
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presenters.RnMListScreenEvent.CharacterClicked
import presenters.RnmListScreenEffect.NavigateToDetail
import presenters.rnMListScreenPresenter
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class RnMListScreenPresenterTest {

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
        moleculeFlow(RecompositionMode.Immediate) {
            rnMListScreenPresenter()
        }.test {
            val (state, effects, eventSink) = awaitItem()
            assertEquals(1, state.characters.size)

            effects.test {
                eventSink(CharacterClicked(state.characters.first()))
                assertEquals(
                    state.characters.first().id,
                    (awaitItem() as NavigateToDetail).characterId
                )
            }
        }
    }
}
