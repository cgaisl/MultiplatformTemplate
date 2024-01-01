package at.cgaisl.template.multiplatform

import android.app.Application
import somethingInitKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        somethingInitKoin()
    }
}
