package com.beehomie.wallpaperjsonbuilder.mappers

import com.beehomie.wallpaperjsonbuilder.domain.models.Banner
import com.beehomie.wallpaperjsonbuilder.local.entities.BannerEntity
import com.beehomie.wallpaperjsonbuilder.remote.dto.BannerDto

fun BannerEntity.toBanner(): Banner {
    return Banner(
        id = id,
        bannerUrl = bannerUrl,
        category = category,
        listIds = listIds,
        openLink = openLink,
        pageName = pageName,
        singleWallpaperId = singleWallpaperId
    )
}

fun BannerEntity.toBannerDto(): BannerDto {
    return BannerDto(
        id = id,
        bannerUrl = bannerUrl,
        category = category,
        listIds = listIds,
        openLink = openLink,
        pageName = pageName,
        singleWallpaperId = singleWallpaperId
    )
}

fun Banner.toBannerEntity(): BannerEntity {
    return BannerEntity(
        id = id,
        bannerUrl = bannerUrl,
        category = category,
        listIds = listIds,
        openLink = openLink,
        pageName = pageName,
        singleWallpaperId = singleWallpaperId
    )
}

fun BannerDto.toBannerEntity(): BannerEntity {
    return BannerEntity(
        id = id ?: 0,
        bannerUrl = bannerUrl ?: "",
        category = category ?: "",
        listIds = listIds ?: emptyList(),
        openLink = openLink ?: "",
        pageName = pageName ?: "",
        singleWallpaperId = singleWallpaperId ?: 0
    )
}