package com.beehomie.wallpaperjsonbuilder.local

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

class CreateDatabase(
    private val builder: RoomDatabase.Builder<WallpaperDB>
) {

    fun getDB() : WallpaperDB {
        return builder
            .fallbackToDestructiveMigration(dropAllTables = true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}