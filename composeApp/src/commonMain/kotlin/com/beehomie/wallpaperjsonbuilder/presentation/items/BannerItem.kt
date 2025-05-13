package com.beehomie.wallpaperjsonbuilder.presentation.items

import com.beehomie.wallpaperjsonbuilder.domain.models.Banner
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiEvents
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.size.Size

@Composable
fun BannerItem(
    modifier: Modifier = Modifier,
    banner: Banner,
    onBannerClick: () -> Unit,
) {
    val bannerImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(banner.bannerUrl)
            .size(Size.ORIGINAL)
            .memoryCacheKey(banner.bannerUrl + banner.id)
            .diskCacheKey(banner.bannerUrl + banner.id)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build(),
        filterQuality = FilterQuality.High
    )

    val bannerImageState by bannerImage.state.collectAsState()
    Box(
        modifier = modifier
            .wrapContentSize()
            .padding(16.dp)
            .aspectRatio(3f/1f)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ){
        when (bannerImageState){
            AsyncImagePainter.State.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Empty")
                }
            }
            is AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error")
                }
            }
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading")
                }
            }
            is AsyncImagePainter.State.Success -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onBannerClick()
                        },
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = bannerImage,
                        contentDescription = "banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}