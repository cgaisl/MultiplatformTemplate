import SwiftUI


struct ContentView: View {
    
    var body: some View {
        TabView {
            DiceScreen()
                .tabItem {
                    VStack {
                        Text("Dice")
                        Image("five_icon").renderingMode(.template)
                    }
                }
            RnMListScreen()
                .tabItem {
                    VStack {
                        Text("Rick and Morty")
                        Image("rnm_icon").renderingMode(.template)
                    }
                }
            
        }
    }
}

#Preview {
    ContentView()
}
