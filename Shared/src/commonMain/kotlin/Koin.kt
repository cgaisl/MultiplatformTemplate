import data.RickAndMortyRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module


fun initKoin() {
    startKoin {
        modules(koinModule)
    }
}


val koinModule = module {
    single { RickAndMortyRepository() }
}
