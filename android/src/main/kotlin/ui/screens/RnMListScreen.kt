package ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ui.LocalNavController
import ui.Screen
import ui.components.RnMListItem

@Composable
fun RnMListScreen() {
    val navController = LocalNavController.current
    val (state, effects, eventSink) = presenters.rnMListScreenPresenter()

    LaunchedEffect(Unit) {
        effects.collect {
            when (it) {
                is presenters.RnmListScreenEffect.NavigateToDetail -> navController.navigate(Screen.RnMDetail.route(it.characterId))
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
    state: presenters.RnMListScreenState,
    eventSink: (presenters.RnMListScreenEvent) -> Unit
) {
    LazyColumn {
        items(state.characters) { character ->
            RnMListItem(
                character = character,
                onClick = { eventSink(presenters.RnMListScreenEvent.CharacterClicked(character)) }
            )
        }
    }
}
