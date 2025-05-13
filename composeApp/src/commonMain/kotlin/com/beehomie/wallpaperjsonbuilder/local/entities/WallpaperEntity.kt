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
    val category: List<String>,
    val name: String,
    val author: String,
    val url : String,
    val thumbnail: String,
    val isPremium: Boolean,
    val tags: List<String>,
    val downloadable: Boolean
)
