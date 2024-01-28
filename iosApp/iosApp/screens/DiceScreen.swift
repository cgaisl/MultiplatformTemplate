import SwiftUI
import Shared
import KMMViewModelCore
import KMMViewModelSwiftUI
import AudioToolbox



struct DiceScreen: View {
    @StateViewModel var viewModel = DiceScreenViewModel()
    
    var body: some View {
        DiceScreenContent(
            state: viewModel.state.value!,
            eventSink: viewModel.eventSink
        )
        .task {
            for await effect in viewModel.effects {
                switch onEnum(of: effect) {
                case .diceRolled:
                    print("Dice Rolled")
                    AudioServicesPlaySystemSound(SystemSoundID(kSystemSoundID_Vibrate))
                case .none:
                    fatalError("Unknown status")
                }
            }
        }
    }
}


struct DiceScreenContent: View {
    let state: DiceScreenState
    let eventSink: (DiceScreenEvent) -> Void
    @State private var rotation: Double = 0

    var body: some View {
        VStack {
            Image("dice\(state.currentDice)")
                .resizable()
                .scaledToFit()
                .frame(width: 100, height: 100)
                .rotationEffect(.degrees(rotation))
                .animation(.easeInOut(duration: 0.2))

            Spacer().frame(height: 56)

            Button(action: {
                eventSink(DiceScreenEventRollDicePressed())
                withAnimation {
                    rollDiceAnimation()
                }
            }) {
                Text("Roll Dice")
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .padding()
    }

    private func rollDiceAnimation() {
        rotation = -30
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.1) {
            rotation = 20
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.1) {
                rotation = -10
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.1) {
                    rotation = 0
                }
            }
        }
    }
}

#Preview {
    DiceScreenContent(state: DiceScreenState(currentDice: 1), eventSink: {_ in})
}

