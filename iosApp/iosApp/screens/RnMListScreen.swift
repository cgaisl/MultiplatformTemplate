import SwiftUI
import Shared
import KMMViewModelSwiftUI


struct RnMListScreen: View {
    @StateViewModel var viewModel = RnMListScreenViewModel()
    
    var body: some View {
        RnMListScreenContent(state: viewModel.state!, event: viewModel.eventSink)
    }
}

struct RnMListScreenContent: View {
    let state: RnMListScreenState
    let event: (RnMListScreenEvent) -> Void
    
    
    var body: some View {
        NavigationView {
            List(state.characters, id: \.id) { character in
                NavigationLink(destination: RnMDetailScreen(characterId: character.id)) {
                    HStack {
                        if let imageUrl = URL(string: character.image) {
                            AsyncImage(url: imageUrl) { image in
                                image.resizable().frame(width: 50, height: 50)
                            } placeholder: {
                                ProgressView()
                            }
                        }
                        Text(character.name)
                    }
                }
            }
            .navigationTitle("Characters")
        }
    }
}

#Preview {
    RnMListScreenContent(state: RnMListScreenState(characters: []), event: {_ in})
}
