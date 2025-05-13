package com.beehomie.wallpaperjsonbuilder.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BeeWallsDto (
    val banners : List<BannerDto>,
    val wallpapers : List<WallpaperDto>
)