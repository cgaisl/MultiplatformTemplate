package ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import at.cgaisl.template.multiplatform.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rendering
import vibratePhone
import viewModels.DiceScreenEffect
import viewModels.DiceScreenEvent
import viewModels.DiceScreenState
import viewModels.DiceScreenViewModel


@Composable
fun DiceScreen() {
    val (state, effects, eventSink) = viewModel<DiceScreenViewModel>().rendering()

    val vibrateFunction = vibratePhone()

    LaunchedEffect(Unit) {
        effects.collect { effect ->
            when (effect) {
                is DiceScreenEffect.DiceRolled -> {
                    vibrateFunction()
                }
            }
        }
    }

    DiceScreeContent(
        state = state,
        eventSink = eventSink,
    )
}

@Composable
fun DiceScreeContent(
    state: DiceScreenState,
    eventSink: (DiceScreenEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var targetRotation by remember { mutableFloatStateOf(0f) }
    val rotation by animateFloatAsState(targetRotation, label = "rotation")

    suspend fun rollDiceAnimation() {
        targetRotation = -30f
        delay(100)
        targetRotation = 20f
        delay(100)
        targetRotation = -10f
        delay(100)
        targetRotation = 0f
    }

    val drawable = when (state.currentDice) {
        1 -> R.drawable.one
        2 -> R.drawable.two
        3 -> R.drawable.three
        4 -> R.drawable.four
        5 -> R.drawable.five
        6 -> R.drawable.six
        else -> throw IllegalStateException("Invalid dice value: ${state.currentDice}")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            modifier = Modifier.rotate(rotation),
        )

        Spacer(modifier = Modifier.height(56.dp))

        ElevatedButton(
            onClick = {
                eventSink(viewModels.DiceScreenEvent.RollDicePressed)
                coroutineScope.launch { rollDiceAnimation() }
            },
        ) {
            Text("Roll Dice")
        }
    }
}

@Preview
@Composable
fun DiceScreenContentPreview() {
    DiceScreeContent(
        state = viewModels.DiceScreenState(currentDice = 1),
        eventSink = {},
    )
}
