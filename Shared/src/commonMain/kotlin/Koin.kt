import data.RickAndMortyRepository
import org.koin.core.module.Module
import org.koin.dsl.module

expect class KoinHelper {
    fun initKoin()
}

fun appModule() = listOf(commonModule, platformModule())

expect fun platformModule(): Module

private val commonModule = module {
    single { RickAndMortyRepository() }
}
