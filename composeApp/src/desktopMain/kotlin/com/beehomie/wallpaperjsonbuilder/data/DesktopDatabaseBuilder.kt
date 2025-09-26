package com.beehomie.wallpaperjsonbuilder.data

import androidx.room.Room
import androidx.room.RoomDatabase
import com.beehomie.wallpaperjsonbuilder.local.WallpaperDB
import java.io.File

fun desktopDatabaseBuilder(): RoomDatabase.Builder<WallpaperDB> {

    val osName = System.getProperty("os.name").lowercase()
    val appName = "com.beehomie.wallpaperjsonbuilder"
    val dbName = "test1_room_db.db"

    val dbFile = when {
        osName.contains("win") -> {
            val appData = System.getenv("APPDATA") ?: "${System.getProperty("user.home")}\\AppData\\Roaming"
            File(appData, "$appName\\$dbName")
        }
        osName.contains("nix") || osName.contains("nux") -> {
            val xdgDataHome = System.getenv("XDG_DATA_HOME") ?: "${System.getProperty("user.home")}/.local/share"
            File(xdgDataHome, "$appName/$dbName")
        }
        else -> {
            File(dbName)
        }
    }

    dbFile.parentFile?.mkdirs()

    return Room.databaseBuilder(
        dbFile.absolutePath
    )

}