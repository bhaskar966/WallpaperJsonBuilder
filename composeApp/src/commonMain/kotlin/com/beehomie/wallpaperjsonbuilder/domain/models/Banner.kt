package com.beehomie.wallpaperjsonbuilder.domain.models

data class Banner(
    val id: Int,
    val bannerUrl: String,
    val category: String,
    val listIds: List<Int>,
    val pageName: String,
    val openLink: String,
    val singleWallpaperId: Int
)
