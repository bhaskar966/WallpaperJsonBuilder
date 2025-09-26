package com.beehomie.wallpaperjsonbuilder.viewModels.components

import com.beehomie.wallpaperjsonbuilder.domain.models.Wallpaper

sealed class WallpaperUiEvents {

    data class UpsertWallpaper(val wallpaper: Wallpaper): WallpaperUiEvents()
    data class UpdateWallpaper(val id: Int): WallpaperUiEvents()
    data class DeleteWallpaper(val id: Int): WallpaperUiEvents()
    data object RefreshData: WallpaperUiEvents()

    data class OnExportButtonClick(val path: String): WallpaperUiEvents()

}