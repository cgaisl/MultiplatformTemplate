package data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import at.cgaisl.multiplatformtemplate.db.RnMDatabaseQueries
import at.cgaisl.template.multiplatform.RickAndMortyCharactersQuery
import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RickAndMortyRepository : KoinComponent {

    private val databaseQueries: RnMDatabaseQueries by inject()

    val characters: StateFlow<List<RnMCharacter>> = databaseQueries
        .selectAllCharacters()
        .asFlow()
        .mapToList(Dispatchers.IO)
        .map { it.map { it.toCharacter() } }
        .stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://rickandmortyapi.com/graphql")
        .build()


    suspend fun reloadCharactersFromNetwork() {
        apolloClient.query(RickAndMortyCharactersQuery())
            .execute()
            .data
            ?.characters
            ?.results
            ?.mapNotNull { it?.toCharacter() }
            ?.let { characters ->
                coroutineScope {
                    characters.map { character ->
                        async {
                            databaseQueries.insertRnMCharacter(
                                id = character.id,
                                name = character.name,
                                image = character.image,
                                origin = character.origin,
                                species = character.species,
                                gender = character.gender,
                                status = character.status,
                            )
                        }
                    }.awaitAll()
                }
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
