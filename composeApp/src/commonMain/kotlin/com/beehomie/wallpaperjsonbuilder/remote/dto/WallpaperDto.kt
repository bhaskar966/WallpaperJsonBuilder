@file:OptIn(ExperimentalSerializationApi::class)

package com.beehomie.wallpaperjsonbuilder.remote.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames


@Serializable
data class WallpaperDto (
    val name: String? = null,
    val author: String? = null,
    val url : String? = null,
    val downloadable: Boolean? = null,
    @JsonNames(names = ["thumbnail", "thumbUrl", "thumb", "url-thumb"])
    val thumbnail: String? = null,
    @JsonNames(names = ["category", "categories"])
    val categoryList: List<String>? = null,
    @SerialName("collections")
    val collectionsString: String? = null,
    @JsonNames(names = ["dimension", "dimensions"])
    val dimension: String? = null,
    val size : Int? = null,
    val copyright: String? = null
)