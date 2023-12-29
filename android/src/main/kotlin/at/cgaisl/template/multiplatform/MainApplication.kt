package at.cgaisl.template.multiplatform

import android.app.Application
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Load modules
            modules(koinModule)
        }
    }
}
