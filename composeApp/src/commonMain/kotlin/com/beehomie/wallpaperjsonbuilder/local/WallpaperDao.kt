package com.beehomie.wallpaperjsonbuilder.local

import androidx.room.*
import com.beehomie.wallpaperjsonbuilder.local.entities.WallpaperEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Dao
interface WallpaperDao {

    @Insert
    suspend fun insertWallpaper(wallpaper: WallpaperEntity)

    @Upsert
    suspend fun upsertWallpaper(wallpaper: WallpaperEntity)

    @Update
    suspend fun updateWallpaper(wallpaper: WallpaperEntity)

    @Delete
    suspend fun deleteWallpaper(wallpapers: WallpaperEntity)

    @Query("SELECT * FROM wallpaper_entity ORDER BY id DESC")
    suspend fun getAllWallpapers(): List<WallpaperEntity>

    @Query("SELECT * FROM wallpaper_entity WHERE id=:id")
    suspend fun findWallpaperById(id: Int): WallpaperEntity


    @Query("DELETE FROM wallpaper_entity")
    suspend fun deleteAllFromWallpaperTable()

    @Query("SELECT category FROM wallpaper_entity")
    suspend fun getAllWallpaperCategories(): List<String>

    @Transaction
    suspend fun clearAllData() {
        deleteAllFromWallpaperTable()
    }

}