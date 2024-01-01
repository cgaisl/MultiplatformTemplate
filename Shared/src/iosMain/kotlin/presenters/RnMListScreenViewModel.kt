package presenters

import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope

class RnMListScreenViewModel : BasePresenterViewModel<RnMListScreenState, RnmListScreenEffect, RnMListScreenEvent>() {
    override fun CoroutineScope.presenter() = launchMolecule(RecompositionMode.Immediate) {
        rnMListScreenPresenter()
    }
}
