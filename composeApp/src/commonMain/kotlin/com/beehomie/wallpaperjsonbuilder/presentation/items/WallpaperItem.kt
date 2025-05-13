package com.beehomie.wallpaperjsonbuilder.presentation.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import com.beehomie.wallpaperjsonbuilder.domain.models.Wallpaper

@Composable
fun WallpaperItem(
    wallpaper: Wallpaper,
    modifier: Modifier = Modifier,
    onWallpaperItemClick: () -> Unit
){
    val sizeResolver = rememberConstraintsSizeResolver()
    val wallpaperImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(wallpaper.thumbnail)
            .size(Size.ORIGINAL)
            .memoryCacheKey(wallpaper.thumbnail +wallpaper.id)
            .diskCacheKey(wallpaper.thumbnail +wallpaper.id)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build(),
        filterQuality = FilterQuality.None
    )

    val wallpaperImageState by wallpaperImage.state.collectAsState()

    Box(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .aspectRatio(9f/16f)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        when (wallpaperImageState){
            AsyncImagePainter.State.Empty -> {
                WallpaperErrorOrEmpty(wallpaper)
            }
            is AsyncImagePainter.State.Error -> {
                WallpaperErrorOrEmpty(wallpaper)
            }
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(22.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "loading",
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
            }
            is AsyncImagePainter.State.Success -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = wallpaperImage,
                        contentDescription = wallpaper.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .then(sizeResolver)
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                onWallpaperItemClick()
                            }
                    )
                }
            }
        }
    }

}

@Composable
fun WallpaperErrorOrEmpty(
    wallpaper: Wallpaper,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(6.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Image not found",
                fontSize = 12.sp
            )
        }

    }
}