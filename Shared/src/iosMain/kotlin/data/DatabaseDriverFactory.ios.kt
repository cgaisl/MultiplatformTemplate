package data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import at.cgaisl.multiplatformtemplate.db.Database

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DriverFactory() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, "RnMDatabase.db")
    }
}
