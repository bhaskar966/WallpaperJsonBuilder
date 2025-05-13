package com.beehomie.wallpaperjsonbuilder.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.beehomie.wallpaperjsonbuilder.local.typeConverters.IntListConverter

@Entity(tableName = "banner_entity")
@TypeConverters(IntListConverter::class)
data class BannerEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val bannerUrl: String,
    val category: String,
    val listIds: List<Int>,
    val pageName: String,
    val openLink: String,
    val singleWallpaperId: Int
)