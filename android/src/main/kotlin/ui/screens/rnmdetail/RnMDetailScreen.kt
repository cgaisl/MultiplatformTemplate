package ui.screens.rnmdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import data.RnMCharacter
import ui.getRendering

@Composable
fun RnMDetailScreen(characterId: String) {
    val (state, _, _) = getRendering { rnMDetailScreenPresenter(characterId) }

    RnMDetailScreenContent(
        state = state,
    )
}

@Composable
fun RnMDetailScreenContent(
    state: RnMDetailScreenState,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(state.character.name, style = androidx.compose.ui.text.TextStyle(fontSize = 24.sp))

        AsyncImage(
            model = state.character.image,
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )

        Row {
            Column {
                Text("Origin: ")
                Text("Species: ")
                Text("Gender: ")
                Text("Status: ")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(state.character.origin)
                Text(state.character.species)
                Text(state.character.gender)
                Text(state.character.status)
            }
        }
    }
}

@Preview
@Composable
fun RnMDetailScreenContentPreview() {
    RnMDetailScreenContent(
        RnMDetailScreenState(
            character = RnMCharacter(
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
