package at.cgaisl.template.multiplatform

import KoinHelper
import android.app.Application

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KoinHelper(this).initKoin()
    }
}
