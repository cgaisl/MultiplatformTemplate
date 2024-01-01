import SwiftUI
import Shared
import KMMViewModelCore
import KMMViewModelSwiftUI


struct ContentView: View {
    @State private var navigateToDetail = false

    var body: some View {
        NavigationView {
            VStack {
                Button("Go to Detail View") {
                    navigateToDetail = true
                }
                .navigationBarTitle("Main View")
                NavigationLink(destination: PresenterView(), isActive: $navigateToDetail) {
                    EmptyView()
                }
            }
        }
    }
}

struct PresenterView: View {
    @StateViewModel var viewModel = DiceScreenPresenterViewModel()

    var body: some View {
        VStack {
            Text("Dice count: \(viewModel.renderingValue.state!.currentDice)")

            Button("Press me ") {
                viewModel.renderingValue.eventSink(DiceScreenEventRollDicePressed())
            }
        }
    }
}

struct OtherView: View {
    var body: some View {
        Text("Hello World")
    }
}
