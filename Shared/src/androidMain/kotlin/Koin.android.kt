import android.content.Context
import at.cgaisl.multiplatformtemplate.db.Database
import data.DriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { Database(DriverFactory(get()).createDriver()).rnMDatabaseQueries }
}

actual class KoinHelper(private val context: Context) {
    actual fun initKoin() {
        startKoin {
            androidContext(context)
            modules(appModule())
        }
    }
}
