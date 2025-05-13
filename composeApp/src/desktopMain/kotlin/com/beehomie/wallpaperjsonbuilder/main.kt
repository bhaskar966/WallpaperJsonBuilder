package com.beehomie.wallpaperjsonbuilder


import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.beehomie.wallpaperjsonbuilder.di.apiModule
import com.beehomie.wallpaperjsonbuilder.di.desktopDatabaseModule
import com.beehomie.wallpaperjsonbuilder.di.dispatcherModule
import com.beehomie.wallpaperjsonbuilder.di.sharedModule
import com.beehomie.wallpaperjsonbuilder.viewModels.MainViewModel
import io.github.vinceglb.filekit.FileKit
import org.koin.compose.koinInject
import org.koin.core.context.startKoin

fun main() {

    startKoin {
        modules(desktopDatabaseModule, sharedModule, apiModule, dispatcherModule)
    }

    FileKit.init(appId = "WallpaperJSONBuilder")

    application {
        val viewmodel = koinInject<MainViewModel>()
        Window(
            onCloseRequest = {
                viewmodel::clearScope.invoke()
                ::exitApplication.invoke()
            },
            title = "WallpaperJSONBuilder",
        ) {
            App()
        }
    }
}