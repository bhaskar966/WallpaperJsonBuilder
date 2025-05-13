package com.beehomie.wallpaperjsonbuilder.mappers

import com.beehomie.wallpaperjsonbuilder.domain.models.Wallpaper
import com.beehomie.wallpaperjsonbuilder.local.entities.WallpaperEntity
import com.beehomie.wallpaperjsonbuilder.remote.dto.WallpaperDto

fun WallpaperDto.toWallpaper(): Wallpaper {
    return Wallpaper(
        id = id ?: -1,
        name = name ?: "",
        category = category ?: emptyList(),
        author = author ?: "",
        url = url ?: "",
        thumbnail = thumbnail ?: "",
        isPremium = isPremium ?: true,
        tags = tags ?: emptyList(),
        downloadable = downloadable ?: true
    )
}

fun WallpaperDto.toWallpaperEntity(): WallpaperEntity {
    return WallpaperEntity(
        id = id ?: -1,
        name = name ?: "",
        category = category ?: emptyList(),
        author = author ?: "",
        url = url ?: "",
        thumbnail = thumbnail ?: "",
        isPremium = isPremium ?: true,
        tags = tags ?: emptyList(),
        downloadable = downloadable ?: true
    )
}


fun Wallpaper.toWallpaperEntity() : WallpaperEntity {
    return WallpaperEntity(
        id = id,
        name = name,
        category = category,
        author = author,
        url = url,
        thumbnail = thumbnail,
        isPremium = isPremium,
        tags = tags,
        downloadable = downloadable
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
        isPremium = isPremium,
        tags = tags,
        downloadable = downloadable
    )
}

fun WallpaperEntity.toWallpaperDto(): WallpaperDto {
    return WallpaperDto(
        id = id,
        name = name,
        category = category,
        author = author,
        url = url,
        thumbnail = thumbnail,
        isPremium = isPremium,
        tags = tags,
        downloadable = downloadable
    )
}