import SwiftUI
import Shared
import KMMViewModelSwiftUI


struct RnMListScreen: View {
    @StateViewModel var viewModel = RnMListScreenViewModel()

    @State var detailIds: [String] = []

    var body: some View {
        NavigationStack(path: $detailIds) {
            RnMListScreenContent(state: viewModel.state.value!, event: viewModel.eventSink)
                .navigationDestination(for: String.self) { detailId in
                    RnMDetailScreen(characterId: detailId)
                }
        }
        .task {
            for await effect in viewModel.effects {
                switch onEnum(of: effect) {
                case .navigateToDetail(let  character):
                    detailIds.append(character.characterId)
                case .none:
                    fatalError("Unknown status")
                }
            }
        }

    }
}

struct RnMListScreenContent: View {
    let state: RnMListScreenState
    let event: (RnMListScreenEvent) -> Void


    var body: some View {
        List(state.characters, id: \.id) { character in
            Button(action: {
                event(RnMListScreenEventCharacterClicked(character: character))
            }) {
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
            .navigationTitle("Characters")
        }
    }
}

#Preview {
    RnMListScreenContent(state: RnMListScreenState(characters: []), event: {_ in})
}
