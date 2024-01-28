package viewModels

import MoleculeViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import data.RnMCharacter
import kotlinx.coroutines.flow.Flow
import org.koin.compose.koinInject


data class RnMDetailScreenState(
    val character: RnMCharacter
)

class RnMDetailScreenViewModel(private val characterId: String) :
    MoleculeViewModel<RnMDetailScreenState, Unit, Unit>() {
    companion object

    @Composable
    override fun state(events: Flow<Unit>, effectSink: (Unit) -> Unit): RnMDetailScreenState {
        val repository = koinInject<data.RickAndMortyRepository>()

        val character = repository.characters.collectAsState().value.first { it.id == characterId }

        return RnMDetailScreenState(character)
    }
}
