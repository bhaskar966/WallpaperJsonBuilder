package com.beehomie.wallpaperjsonbuilder.data

import androidx.room.Room
import androidx.room.RoomDatabase
import com.beehomie.wallpaperjsonbuilder.local.WallpaperDB
import java.io.File

fun desktopDatabaseBuilder(): RoomDatabase.Builder<WallpaperDB> {

//    val dbFile = File(System.getProperty("java.io.tempdir"), "room_db.db")

    val osName = System.getProperty("os.name").lowercase()
    val appName = "com.beehomie.wallpaperjsonbuilder"

    val dbFile = when {
        osName.contains("win") -> {
            val appData = System.getenv("APPDATA") ?: "${System.getProperty("user.home")}\\AppData\\Roaming"
            File(appData, "$appName\\room_db.db")
        }
        osName.contains("nix") || osName.contains("nux") -> {
            val xdgDataHome = System.getenv("XDG_DATA_HOME") ?: "${System.getProperty("user.home")}/.local/share"
            File(xdgDataHome, "$appName/room_db.db")
        }
        else -> {
            File("room_db.db")
        }
    }

    dbFile.parentFile?.mkdirs()

    return Room.databaseBuilder(
        dbFile.absolutePath
    )

}