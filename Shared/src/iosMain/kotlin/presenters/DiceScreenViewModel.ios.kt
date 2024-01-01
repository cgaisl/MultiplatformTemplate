package presenters

import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope


class DiceScreenPresenterViewModel : BasePresenterViewModel<DiceScreenState, DiceScreenEffect, DiceScreenEvent>() {
    override fun CoroutineScope.presenter() = launchMolecule(RecompositionMode.Immediate) {
        diceScreenPresenter()
    }
}
