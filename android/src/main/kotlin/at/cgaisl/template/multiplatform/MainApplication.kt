package at.cgaisl.template.multiplatform

import android.app.Application
import initKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}
