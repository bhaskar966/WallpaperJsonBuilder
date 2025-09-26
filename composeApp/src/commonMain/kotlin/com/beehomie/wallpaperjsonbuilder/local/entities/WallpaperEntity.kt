package com.beehomie.wallpaperjsonbuilder.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.beehomie.wallpaperjsonbuilder.local.typeConverters.StringListConverter

@Entity(tableName = "wallpaper_entity")
@TypeConverters(StringListConverter::class)
data class WallpaperEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
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
