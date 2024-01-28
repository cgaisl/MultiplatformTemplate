package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.compose.AsyncImage
import rendering
import viewModels.RnMDetailScreenState
import viewModels.RnMDetailScreenViewModel

@Composable
fun RnMDetailScreen(characterId: String) {
    val (state, _, _) = viewModel<RnMDetailScreenViewModel>(factory = viewModelFactory {
        initializer { RnMDetailScreenViewModel(characterId) }
    }).rendering()

    RnMDetailScreenContent(
        state = state,
    )
}

@Composable
fun RnMDetailScreenContent(
    state: RnMDetailScreenState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
            character = data.RnMCharacter(
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
