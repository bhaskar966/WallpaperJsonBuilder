package com.beehomie.wallpaperjsonbuilder.domain.models

import androidx.room.PrimaryKey

data class Wallpaper(
    val id: Int,
    val category: List<String>? = null,
    val name: String,
    val author: String? = null,
    val url : String,
    val thumbnail: String? = null,
    val size: Int? = null,
    val dimensions: String? = null,
    val downloadable: Boolean? = null,
    val copyright: String? = null
)