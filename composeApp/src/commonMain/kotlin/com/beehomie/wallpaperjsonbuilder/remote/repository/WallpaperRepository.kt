package com.beehomie.wallpaperjsonbuilder.remote.repository

import com.beehomie.wallpaperjsonbuilder.domain.models.Banner
import com.beehomie.wallpaperjsonbuilder.domain.models.Wallpaper
import com.beehomie.wallpaperjsonbuilder.local.entities.BannerEntity
import com.beehomie.wallpaperjsonbuilder.local.entities.WallpaperEntity
import com.beehomie.wallpaperjsonbuilder.remote.dto.BeeWallsDto
import kotlinx.coroutines.flow.Flow
import java.io.File

interface WallpaperRepository {

    suspend fun insertWallpaper(wallpaper: WallpaperEntity)

    suspend fun upsertWallpaper(wallpaper: WallpaperEntity)

    suspend fun updateWallpaper(id: Int)

    suspend fun deleteWallpaper(id: Int)

    suspend fun insertBanner(banner: BannerEntity)

    suspend fun upsertBanner(banner: BannerEntity)

    suspend fun updateBanner(id: Int)

    suspend fun deleteBanner(id: Int)

    suspend fun getAllWallpapers(): Flow<List<Wallpaper>>

    suspend fun getAllBanners(): Flow<List<Banner>>

    suspend fun getJson(file: File? = null, link: String? = null): BeeWallsDto

    suspend fun loadData(file: File? = null, link: String? = null)

    suspend fun getAllTags(): List<String>

    suspend fun getAllCategories(): List<String>

    suspend fun writeLocalFile(path: String)

    suspend fun clearAllData()

}