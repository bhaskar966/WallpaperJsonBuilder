package com.beehomie.wallpaperjsonbuilder.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class WallpaperDto (
    val id: Int? = null,
    val category: List<String>? = null,
    val name: String? = null,
    val author: String? = null,
    val url : String? = null,
    val thumbnail: String? = null,
    val isPremium: Boolean? = null,
    val tags: List<String>? = null,
    val downloadable: Boolean? = null
)