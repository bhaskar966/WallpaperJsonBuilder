package com.beehomie.wallpaperjsonbuilder.local

import androidx.room.*
import com.beehomie.wallpaperjsonbuilder.local.entities.BannerEntity
import com.beehomie.wallpaperjsonbuilder.local.entities.WallpaperEntity
import com.beehomie.wallpaperjsonbuilder.local.typeConverters.IntListConverter
import com.beehomie.wallpaperjsonbuilder.local.typeConverters.StringListConverter


@Database(
    entities = [
        WallpaperEntity::class,
        BannerEntity::class
    ],
    version = 2,
    exportSchema = true
)
@ConstructedBy(WallpaperDataBaseConstructor::class)
@TypeConverters(StringListConverter::class, IntListConverter::class)
abstract class WallpaperDB: RoomDatabase() {
    abstract fun wallpaperDao(): WallpaperDao
}

@Suppress("KotlinNoActualForExpect")
expect object WallpaperDataBaseConstructor: RoomDatabaseConstructor<WallpaperDB>{
    override fun initialize(): WallpaperDB
}