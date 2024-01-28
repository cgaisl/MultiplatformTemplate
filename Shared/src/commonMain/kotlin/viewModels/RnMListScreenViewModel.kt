package viewModels

import MoleculeViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import data.RickAndMortyRepository
import data.RnMCharacter
import kotlinx.coroutines.flow.Flow
import org.koin.compose.koinInject

data class RnMListScreenState(
    val characters: List<RnMCharacter>
)

sealed interface RnmListScreenEffect {
    class NavigateToDetail(val characterId: String) : RnmListScreenEffect
}

sealed interface RnMListScreenEvent {
    data class CharacterClicked(val character: RnMCharacter) : RnMListScreenEvent
}

class RnMListScreenViewModel : MoleculeViewModel<RnMListScreenState, RnmListScreenEffect, RnMListScreenEvent>() {

    @Composable
    override fun state(
        events: Flow<RnMListScreenEvent>,
        effectSink: (RnmListScreenEffect) -> Unit
    ): RnMListScreenState {
        val repository = koinInject<RickAndMortyRepository>()
        val characters = repository.characters.collectAsState().value

        LaunchedEffect(Unit) {
            repository.reloadCharactersFromNetwork()
        }

        LaunchedEffect(Unit) {
            events.collect { event ->
                when (event) {
                    is RnMListScreenEvent.CharacterClicked -> {
                        effectSink(RnmListScreenEffect.NavigateToDetail(event.character.id))
                    }
                }
            }
        }

        return RnMListScreenState(characters)
    }
}
