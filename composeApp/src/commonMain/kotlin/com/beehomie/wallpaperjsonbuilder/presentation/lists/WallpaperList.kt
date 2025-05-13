package com.beehomie.wallpaperjsonbuilder.presentation.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import com.beehomie.wallpaperjsonbuilder.presentation.items.WallpaperItem
import com.beehomie.wallpaperjsonbuilder.presentation.dialogs.WallpaperModificationDialog
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiEvents
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiState

@Composable
fun WallpaperListUi(
    wallpaperUiState: WallpaperUiState,
    windowState: WindowState,
    onWallpaperClick: (WallpaperUiEvents) -> Unit
) {

    val scrollState = rememberLazyGridState()
    val wallpapers = remember(wallpaperUiState) { wallpaperUiState.wallpapers }
    val currentSize by remember { mutableStateOf(windowState.size) }

    LaunchedEffect(wallpaperUiState){
        if(wallpaperUiState.wallpapers.isEmpty()){
            return@LaunchedEffect
        }
    }

    val isWallpaperModDialogVisible = remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
    ){
        if(wallpapers.isEmpty()){
            Text("Hello Mars")
            return
        }

        val selectedWallpaper = remember(wallpaperUiState.wallpapers) { mutableStateOf(wallpapers[0]) }
        WallpaperModificationDialog(
            isWallpaperWallpaperModDialogVisible = isWallpaperModDialogVisible,
            selectedWallpaper = selectedWallpaper.value,
            onWallpaperUiEvents = onWallpaperClick,
            wallpaperUiState = wallpaperUiState
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            state = scrollState,
            modifier = Modifier.heightIn(currentSize.height)
        ){

            items(wallpapers, key = { it.id }){
                WallpaperItem(
                    wallpaper = it,
                    onWallpaperItemClick = {
                        selectedWallpaper.value = it
                        isWallpaperModDialogVisible.value = !isWallpaperModDialogVisible.value
                    }
                )
            }
        }
    }

}