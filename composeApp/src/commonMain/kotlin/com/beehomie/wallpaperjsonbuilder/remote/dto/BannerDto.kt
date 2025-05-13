package com.beehomie.wallpaperjsonbuilder.remote.dto

import kotlinx.serialization.Serializable

@Serializable
class BannerDto (
    val id: Int? = null,
    val bannerUrl: String? = null,
    val category: String? = null,
    val listIds: List<Int>? = null,
    val pageName: String? = null,
    val openLink: String? = null,
    val singleWallpaperId: Int? = null
)