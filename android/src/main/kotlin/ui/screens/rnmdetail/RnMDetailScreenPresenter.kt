package ui.screens.rnmdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import data.RickAndMortyRepository
import data.RnMCharacter
import kotlinx.coroutines.flow.emptyFlow
import org.koin.compose.koinInject
import ui.Rendering

data class RnMDetailScreenState(
    val character: RnMCharacter
)

@Composable
fun rnMDetailScreenPresenter(
    characterId: String,
): Rendering<RnMDetailScreenState, Unit, Unit> {
    val repository = koinInject<RickAndMortyRepository>()

    val character = repository.characters.collectAsState().value.first { it.id == characterId }

    return Rendering(
        state = RnMDetailScreenState(character),
        effects = emptyFlow()
    ) { }
}
