package com.beehomie.wallpaperjsonbuilder.viewModels.components

import com.beehomie.wallpaperjsonbuilder.domain.models.Wallpaper

data class WallpaperUiState(
    val wallpapers: List<Wallpaper> = emptyList(),
    val isDbEmpty: Boolean = false,
    val needsData: Boolean = false,
    val existingCategoryList: List<String> = emptyList(),
)