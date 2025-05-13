package com.beehomie.wallpaperjsonbuilder.domain.models

import androidx.room.PrimaryKey

data class Wallpaper(
    val id: Int,
    val category: List<String>,
    val name: String,
    val author: String,
    val url: String,
    val thumbnail: String,
    val isPremium: Boolean,
    val tags: List<String>,
    val downloadable: Boolean
)