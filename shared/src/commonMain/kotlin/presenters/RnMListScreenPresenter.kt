package presenters

import Rendering
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import data.RickAndMortyRepository
import data.RnMCharacter
import kotlinx.coroutines.flow.MutableSharedFlow
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


@Composable
fun rnMListScreenPresenter(): Rendering<RnMListScreenState, RnmListScreenEffect, RnMListScreenEvent> {
    val effects = remember { MutableSharedFlow<RnmListScreenEffect>(extraBufferCapacity = 20) }
    val repository = koinInject<RickAndMortyRepository>()

    LaunchedEffect(Unit) {
        repository.loadCharacters()
    }

    return Rendering(
        state = RnMListScreenState(repository.characters.collectAsState().value),
        effects = effects
    ) { event ->
        when (event) {
            is RnMListScreenEvent.CharacterClicked -> {
                effects.tryEmit(RnmListScreenEffect.NavigateToDetail(event.character.id))
            }
        }
    }
}
