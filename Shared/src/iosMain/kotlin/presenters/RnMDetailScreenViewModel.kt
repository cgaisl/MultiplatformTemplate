package presenters

import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope


class RnMDetailScreenViewModel(val characterId: String) : BasePresenterViewModel<RnMDetailScreenState, Unit, Unit>() {
    override fun CoroutineScope.presenter() = launchMolecule(RecompositionMode.Immediate) {
        rnMDetailScreenPresenter(characterId)
    }
}
