package com.beehomie.wallpaperjsonbuilder.di

import androidx.room.RoomDatabase
import com.beehomie.wallpaperjsonbuilder.local.WallpaperDB
import com.beehomie.wallpaperjsonbuilder.data.desktopDatabaseBuilder
import org.koin.dsl.module

val desktopDatabaseModule = module {
    single < RoomDatabase.Builder<WallpaperDB> > { desktopDatabaseBuilder() }
}