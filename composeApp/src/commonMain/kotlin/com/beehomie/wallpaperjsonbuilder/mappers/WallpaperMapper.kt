package com.beehomie.wallpaperjsonbuilder.mappers

import com.beehomie.wallpaperjsonbuilder.domain.models.Wallpaper
import com.beehomie.wallpaperjsonbuilder.local.entities.WallpaperEntity
import com.beehomie.wallpaperjsonbuilder.remote.dto.WallpaperDto

private fun WallpaperDto.getConsolidatedCategories(): List<String> {
    // Combine the list from `category` and the comma-separated string from `collections`
    val fromList = categoryList ?: emptyList()
    val fromString = collectionsString?.split(',')?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList()
    return (fromList + fromString).distinct()
}

fun WallpaperDto.toWallpaper(): Wallpaper {
    return Wallpaper(
        id = 0, // Not from DB, so no ID yet
        name = name ?: "",
        category = getConsolidatedCategories(),
        author = author ?: "",
        url = url ?: "",
        thumbnail = thumbnail,
        size = size ?: 0,
        dimensions = dimension,
        downloadable = downloadable ?: true,
        copyright = copyright ?: ""
    )
}

fun WallpaperDto.toWallpaperEntity(): WallpaperEntity {
    return WallpaperEntity(
        // Let Room auto-generate the ID by using the default value (0)
        name = name ?: "",
        category = getConsolidatedCategories(),
        author = author ?: "",
        url = url ?: "",
        thumbnail = thumbnail,
        size = size,
        dimensions = dimension,
        downloadable = downloadable ?: true,
        copyright = copyright
    )
}


fun Wallpaper.toWallpaperEntity() : WallpaperEntity {
    return WallpaperEntity(
        id = if (id > 0) id else 0, // Let Room generate ID if it's a new item
        name = name,
        category = category,
        author = author,
        url = url,
        thumbnail = thumbnail,
        size = size,
        dimensions = dimensions,
        downloadable = downloadable,
        copyright = copyright
    )
}

fun WallpaperEntity.toWallpaper(): Wallpaper {
    return Wallpaper(
        id = id,
        name = name,
        category = category,
        author = author,
        url = url,
        thumbnail = thumbnail,
        size = size,
        dimensions = dimensions,
        downloadable = downloadable,
        copyright = copyright
    )
}

fun WallpaperEntity.toWallpaperDto(): WallpaperDto {
    return WallpaperDto(
        name = name,
        categoryList = category,
        author = author,
        url = url,
        thumbnail = thumbnail,
        size = size,
        dimension = dimensions,
        downloadable = downloadable,
        copyright = copyright
    )
}