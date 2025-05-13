package com.beehomie.wallpaperjsonbuilder.presentation.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.WindowState
import com.beehomie.wallpaperjsonbuilder.presentation.dialogs.BannerModificationDialog
import com.beehomie.wallpaperjsonbuilder.presentation.items.BannerItem
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiEvents
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiState

@Composable
fun BannerListUi(
    wallpaperUiState: WallpaperUiState,
    windowState: WindowState,
    onWallpaperClick: (WallpaperUiEvents) -> Unit
) {

    val scrollState = rememberLazyGridState()
    val banners = remember(wallpaperUiState) { wallpaperUiState.banners }
    val currentSize by remember { mutableStateOf(windowState.size) }


    val isBannerModDialogVisible = remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth(0.5f)
    ){
        if(banners.isEmpty()){
            Text("Hello Mars")
        }

        val selectedBanner = remember { mutableStateOf(banners[0]) }
        BannerModificationDialog(
            isBannerModDialogVisible = isBannerModDialogVisible,
            selectedBanner = selectedBanner.value,
            onWallpaperUiEvents = onWallpaperClick
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            state = scrollState,
            modifier = Modifier.heightIn(currentSize.height)
        ){

            items(banners, key = { it.id }){
                BannerItem(
                    banner = it,
                    onBannerClick = {
                        selectedBanner.value = it
                        isBannerModDialogVisible.value = !isBannerModDialogVisible.value
                    }
                )
            }
        }
    }

}