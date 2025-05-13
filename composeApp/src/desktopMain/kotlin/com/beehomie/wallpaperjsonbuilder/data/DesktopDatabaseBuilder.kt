package com.beehomie.wallpaperjsonbuilder.data

import androidx.room.Room
import androidx.room.RoomDatabase
import com.beehomie.wallpaperjsonbuilder.local.WallpaperDB
import java.io.File

fun desktopDatabaseBuilder(): RoomDatabase.Builder<WallpaperDB> {

    val dbFile = File(System.getProperty("java.io.tempdir"), "room_db.db")

    return Room.databaseBuilder(
        dbFile.absolutePath
    )

}