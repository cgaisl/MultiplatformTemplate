package presenters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.compose.koinInject


data class RnMDetailScreenState(
    val character: data.RnMCharacter
)

@Composable
fun rnMDetailScreenPresenter(
    characterId: String,
): RnMDetailScreenState {
    val repository = koinInject<data.RickAndMortyRepository>()

    val character = repository.characters.collectAsState().value.first { it.id == characterId }

    return RnMDetailScreenState(character)
}
