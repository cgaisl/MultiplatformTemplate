import SwiftUI
import Shared
import KMMViewModelSwiftUI


struct RnMDetailScreen: View {
    
    @StateViewModel var viewModel: RnMDetailScreenViewModel
    
    init(characterId: String) {
        _viewModel = StateViewModel(wrappedValue: RnMDetailScreenViewModel(characterId: characterId))
    }
    
    var body: some View {
        RnMDetailScreenContent(state: viewModel.state!)
    }
}

struct RnMDetailScreenContent: View {
    let state: RnMDetailScreenState
    
    var body: some View {
        VStack(alignment: .center, spacing: 20) {
            Text(state.character.name)
                    .font(.title)

            if let imageUrl = URL(string: state.character.image) {
                    AsyncImage(url: imageUrl) { image in
                        image.resizable().frame(width: 80, height: 80)
                    } placeholder: {
                        ProgressView()
                    }
                }

                HStack {
                    VStack(alignment: .leading) {
                        Text("Origin:")
                        Text("Species:")
                        Text("Gender:")
                        Text("Status:")
                    }
                    VStack(alignment: .leading) {
                        Text(state.character.origin)
                        Text(state.character.species)
                        Text(state.character.gender)
                        Text(state.character.status)
                    }
                }
            }
            .padding()
            .navigationTitle(state.character.name)
            .navigationBarTitleDisplayMode(.inline)
    }
}
