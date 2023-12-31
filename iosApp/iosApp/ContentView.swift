import SwiftUI
import KMMViewModelSwiftUI
import Shared

struct ContentView: View {
    
    @StateViewModel var viewModel = DiceViewModel()
    
    @State private var showContent = false
    var body: some View {
        Text("Hello World: \(viewModel.getRendering.state!.currentDice)")
        
        Button("Press me ") {
            viewModel.getRendering.eventSink(DiceScreenEventRollDicePressed())
        }
//            .task {
//                for await rendering in viewModel.rendering {
//                    print(rendering.state?.currentDice)
//                    if rendering.state?.currentDice == 6 {
//                        break
//                    }
//                    rendering.eventSink(DiceScreenEventRollDicePressed())
//                }
//            }
//        VStack {
//            Button("Click me!") {
//                withAnimation {
//                    showContent = !showContent
//                }
//            }
//
//            if showContent {
//                VStack(spacing: 16) {
//                    Image(systemName: "swift")
//                        .font(.system(size: 200))
//                        .foregroundColor(.accentColor)
//                    Text("SwiftUI: ")
//                }
//                .transition(.move(edge: .top).combined(with: .opacity))
//            }
//        }
//        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
//        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
