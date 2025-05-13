package com.beehomie.wallpaperjsonbuilder

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.rememberWindowState
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import coil3.util.Logger
import com.beehomie.wallpaperjsonbuilder.presentation.MainScreen
import okio.Path
import okio.Path.Companion.toPath
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.io.File
import kotlin.io.path.Path

@Composable
@Preview
fun App() {

    val imageCacheDir: Path = ("${System.getProperty("user.home")}/.my_app/image_cache").toPath()
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .diskCache {
                DiskCache.Builder()
                    .directory(imageCacheDir)
                    .maxSizePercent(0.02)
                    .build()
            }
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.20)
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }

    MaterialTheme {
        val windowState = rememberWindowState()
        MainScreen(windowState)
    }
}