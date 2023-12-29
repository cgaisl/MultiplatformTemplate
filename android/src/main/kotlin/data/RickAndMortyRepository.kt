package data

import at.cgaisl.template.multiplatform.RickAndMortyCharactersQuery
import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RickAndMortyRepository {
    private val _characters = MutableStateFlow(emptyList<RnMCharacter>())
    val characters: StateFlow<List<RnMCharacter>> = _characters

    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .build()


    suspend fun loadCharacters() {
        apolloClient.query(RickAndMortyCharactersQuery()).execute().data?.characters?.results?.let {
            _characters.value = it.mapNotNull { it?.toCharacter() }
        }
    }
}

fun RickAndMortyCharactersQuery.Result.toCharacter() = RnMCharacter(
    id = id ?: "unknown",
    name = name ?: "unknown",
    image = image ?: "",
    origin = origin?.name ?: "unknown",
    species = species ?: "unknown",
    gender = gender ?: "unknown",
    status = status ?: "unknown",
)
