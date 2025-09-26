package com.beehomie.wallpaperjsonbuilder.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import com.beehomie.wallpaperjsonbuilder.presentation.FilePickers.FilePickerSection
import com.beehomie.wallpaperjsonbuilder.presentation.forms.WallpaperInputForm
import com.beehomie.wallpaperjsonbuilder.presentation.lists.WallpaperListUi
import com.beehomie.wallpaperjsonbuilder.presentation.menu.MenuItem
import com.beehomie.wallpaperjsonbuilder.presentation.menu.MenuItemNames
import com.beehomie.wallpaperjsonbuilder.presentation.menu.MoreOptions
import com.beehomie.wallpaperjsonbuilder.viewModels.MainViewModel
import io.github.vinceglb.filekit.absolutePath
import io.github.vinceglb.filekit.dialogs.compose.rememberDirectoryPickerLauncher
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun MainScreen(
    windowState: WindowState
) {
    val viewModel = koinInject<MainViewModel>()
    val scope = rememberCoroutineScope()
    val filePicker = rememberFilePickerLauncher { file ->
        scope.launch {
            viewModel.loadData(file?.file)
        }
    }
    val dirPicker = rememberDirectoryPickerLauncher { dir ->
        scope.launch {
            dir?.absolutePath()?.let { path ->
                viewModel.exportFile(path)
            } ?: print("Export Path is null")
        }
    }
    var selectedMenu by remember { mutableStateOf(MenuItemNames.WALLPAPERS) }
    val isWallpaperInsertDialogVisible = remember { mutableStateOf(false) }
    val isBannerInsertDialogVisible = remember { mutableStateOf(false) }

    val wallpaperUiState by viewModel.wallpaperUiState.collectAsState()

    val wallpapers = wallpaperUiState.wallpapers

    LaunchedEffect(true){
        viewModel.checkForData()
//        viewModel.loadData()
    }

    LaunchedEffect(wallpapers.size) {
        print("mainScreen: Wallpaper size = ${wallpapers.size}")
    }

    var wallpaperNameTextField = remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(200.dp)
                .fillMaxHeight()
                .background(Color.LightGray)
                .padding(16.dp)
        ){
            MenuItemNames.entries.forEach { menuItemName ->
                MenuItem(
                    label = menuItemName.displayName,
                    isSelected = selectedMenu == menuItemName,
                    onClick = {
                        selectedMenu = menuItemName
                    }
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
        ){
            when(selectedMenu){
                MenuItemNames.IMPORT_DATA -> {
                    FilePickerSection(
                        wallpaperUiState = wallpaperUiState,
                        onFilePickerClick = {
                            filePicker.launch()
                        },
                        onLinkSubmitClick = { link ->
                            scope.launch {
                                viewModel.loadData(link = link)
                            }
                        }
                    )
                }
                MenuItemNames.WALLPAPERS -> {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){

                        Button(
                            onClick = {
                                isWallpaperInsertDialogVisible.value = true
                            }
                        ){
                            Text("Insert New Wallpaper")
                        }

                        WallpaperInputForm(
                            isFromVisible = isWallpaperInsertDialogVisible,
                            onInsertButtonClick = viewModel::onEvent,
                            wallpaperUiState = wallpaperUiState
                        )
                        WallpaperListUi(
                            wallpaperUiState = wallpaperUiState,
                            windowState = windowState,
                            onWallpaperClick = viewModel::onEvent
                        )
                    }

                }

                MenuItemNames.MORE_OPTIONS -> {
                    MoreOptions(
                        clearButtonClick = {
                            scope.launch {
                                viewModel.clearAllData()
                            }
                        },
                        exportButtonClick = {
                            dirPicker.launch()
                        },
                        onSynthesizeClick = {
                            scope.launch {
                                viewModel.sanitizeLinks()
                            }
                        }
                    )
                }
            }
        }
    }
}

