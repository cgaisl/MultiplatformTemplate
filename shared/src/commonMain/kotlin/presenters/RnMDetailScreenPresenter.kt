package presenters

import Rendering
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.emptyFlow
import org.koin.compose.koinInject


data class RnMDetailScreenState(
    val character: data.RnMCharacter
)

@Composable
fun rnMDetailScreenPresenter(
    characterId: String,
): Rendering<RnMDetailScreenState, Unit, Unit> {
    val repository = koinInject<data.RickAndMortyRepository>()

    val character = repository.characters.collectAsState().value.first { it.id == characterId }

    return Rendering(
        state = RnMDetailScreenState(character),
        effects = emptyFlow()
    ) { }
}
