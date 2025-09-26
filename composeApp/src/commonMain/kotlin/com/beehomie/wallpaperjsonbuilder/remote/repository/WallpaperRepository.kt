package com.beehomie.wallpaperjsonbuilder.remote.repository

import com.beehomie.wallpaperjsonbuilder.domain.models.Wallpaper
import com.beehomie.wallpaperjsonbuilder.local.entities.WallpaperEntity
import com.beehomie.wallpaperjsonbuilder.remote.dto.WallpaperDto
import kotlinx.coroutines.flow.Flow
import java.io.File

interface WallpaperRepository {

    suspend fun insertWallpaper(wallpaper: WallpaperEntity)

    suspend fun upsertWallpaper(wallpaper: WallpaperEntity)

    suspend fun updateWallpaper(id: Int)

    suspend fun deleteWallpaper(id: Int)

    suspend fun getAllWallpapers(): Flow<List<Wallpaper>>

    suspend fun getJson(file: File? = null, link: String? = null): List<WallpaperDto>

    suspend fun loadData(file: File? = null, link: String? = null)

    suspend fun getAllCategories(): List<String>

    suspend fun writeLocalFile(path: String)

    suspend fun clearAllData()

    suspend fun sanitizeLinks()

}