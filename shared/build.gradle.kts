plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.molecule)
    alias(libs.plugins.apolloPlugin)
    alias(libs.plugins.skie)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    jvmToolchain(17)

    androidTarget ()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.compose)
            implementation(libs.coroutines)
            implementation(libs.apollo.runtime)
            implementation(libs.sql.delight.coroutines.extensions)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.compose.runtime.saveable.android)
            implementation(libs.sql.delight.driver.android)
            implementation(libs.koin.android)
        }
        nativeMain.dependencies {
            api(libs.kmm.viewmodel)
            implementation(libs.sql.delight.driver.ios)
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.turbine)
                implementation(libs.koin.test)
                implementation(libs.mockk)
            }
        }
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
    }
}

android {
    namespace = libs.versions.android.packageName.get() + ".shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

apollo {
    service("RickAndMorty") {
        packageName.set(libs.versions.android.packageName.get())
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("at.cgaisl.multiplatformtemplate.db")
        }
    }
}
