package com.beehomie.wallpaperjsonbuilder.local

import androidx.room.*
import com.beehomie.wallpaperjsonbuilder.local.entities.BannerEntity
import com.beehomie.wallpaperjsonbuilder.local.entities.WallpaperEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Dao
interface WallpaperDao {

    @Insert
    suspend fun insertWallpaper(wallpaper: WallpaperEntity)

    @Insert
    suspend fun insertBanner(banner: BannerEntity)

    @Upsert
    suspend fun upsertWallpaper(wallpaper: WallpaperEntity)

    @Upsert
    suspend fun upsertBanner(banner: BannerEntity)

    @Update
    suspend fun updateWallpaper(wallpaper: WallpaperEntity)

    @Update
    suspend fun updateBanner(banner: BannerEntity)

    @Delete
    suspend fun deleteWallpaper(wallpapers: WallpaperEntity)

    @Delete
    suspend fun deleteBanner(banner: BannerEntity)

    @Query("SELECT * FROM wallpaper_entity ORDER BY id DESC")
    suspend fun getAllWallpapers(): List<WallpaperEntity>

    @Query("SELECT * FROM banner_entity")
    suspend fun getAllBanners(): List<BannerEntity>

    @Query("SELECT * FROM wallpaper_entity WHERE id=:id")
    suspend fun findWallpaperById(id: Int): WallpaperEntity

    @Query("SELECT * FROM banner_entity WHERE id=:id")
    suspend fun findBannerById(id: Int): BannerEntity

    @Query("DELETE FROM wallpaper_entity")
    suspend fun deleteAllFromWallpaperTable()

    @Query("DELETE FROM banner_entity")
    suspend fun deleteAllFromBannerTable()

    @Query("SELECT category FROM wallpaper_entity")
    suspend fun getAllWallpaperCategories(): List<String>

    @Query("SELECT tags FROM wallpaper_entity")
    suspend fun getAllWallpaperTags(): List<String>

    @Transaction
    suspend fun clearAllData() {
        deleteAllFromWallpaperTable()
        deleteAllFromBannerTable()
    }

}