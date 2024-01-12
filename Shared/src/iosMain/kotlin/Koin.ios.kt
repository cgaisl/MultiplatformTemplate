import at.cgaisl.multiplatformtemplate.db.Database
import data.DriverFactory
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { Database(DriverFactory().createDriver()).rnMDatabaseQueries }
}

actual class KoinHelper() {
    actual fun initKoin() {
        startKoin {
            modules(appModule())
        }
    }
}
