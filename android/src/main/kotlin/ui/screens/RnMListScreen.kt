package ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import rendering
import ui.LocalNavController
import ui.Screen
import ui.components.RnMListItem
import viewModels.RnMListScreenViewModel

@Composable
fun RnMListScreen() {
    val navController = LocalNavController.current
    val (state, effects, eventSink) = viewModel<RnMListScreenViewModel>().rendering()

    LaunchedEffect(Unit) {
        effects.collect {
            when (it) {
                is viewModels.RnmListScreenEffect.NavigateToDetail -> navController.navigate(Screen.RnMDetail.route(it.characterId))
            }
        }
    }

    RnMListScreenContent(
        state = state,
        eventSink = eventSink,
    )
}

@Composable
fun RnMListScreenContent(
    state: viewModels.RnMListScreenState,
    eventSink: (viewModels.RnMListScreenEvent) -> Unit
) {
    LazyColumn {
        items(state.characters) { character ->
            RnMListItem(
                character = character,
                onClick = { eventSink(viewModels.RnMListScreenEvent.CharacterClicked(character)) }
            )
        }
    }
}
