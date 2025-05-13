package com.beehomie.wallpaperjsonbuilder.presentation.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.beehomie.wallpaperjsonbuilder.domain.models.Banner
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiEvents
import com.beehomie.wallpaperjsonbuilder.viewModels.components.formStates.BannerFormState
import kotlin.text.ifEmpty

@Composable
fun BannerModificationDialog(
    selectedBanner: Banner,
    isBannerModDialogVisible: MutableState<Boolean>,
    onWallpaperUiEvents: (WallpaperUiEvents) -> Unit
) {

    val form = remember { BannerFormState() }

    LaunchedEffect(selectedBanner) {
        form.bannerUrl = selectedBanner.bannerUrl
        form.category = selectedBanner.category
        form.listIds = selectedBanner.listIds.joinToString(", ")
        form.pageName = selectedBanner.pageName
        form.openLink = selectedBanner.openLink
        form.singleWallpaperId = selectedBanner.singleWallpaperId.toString()
    }

    AnimatedVisibility(
        visible = isBannerModDialogVisible.value
    ) {
        AlertDialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            modifier = Modifier
                .padding(
                    horizontal = 50.dp,
                    vertical = 15.dp
                ),
            onDismissRequest = {
                isBannerModDialogVisible.value = false
            },
            title = {
                Text(
                    "Edit Banner",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        model = form.bannerUrl,
                        contentDescription = "Wallpaper Preview",
                        modifier = Modifier
                            .weight(1f)
                            .height(180.dp)
                            .aspectRatio(4f/3f)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Right: Editable Form
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = Modifier
                            .weight(2f)
                    ) {
                        Text("id : ${selectedBanner.id}")
                        TextField(value = form.bannerUrl, onValueChange = { form.bannerUrl = it }, label = {
                            Text(
                                "Banner URL"
                            )
                        })
                        TextField(value = form.category, onValueChange = { form.category = it }, label = {
                            Text(
                                "Category"
                            )
                        })
                        TextField(value = form.listIds, onValueChange = { form.listIds = it }, label = {
                            Text(
                                "List Ids (comma-separated)"
                            )
                        })
                        TextField(value = form.openLink, onValueChange = { form.openLink = it }, label = {
                            Text(
                                "Open Link"
                            )
                        })
                        TextField(value = form.pageName, onValueChange = { form.pageName = it }, label = {
                            Text(
                                "Page Name"
                            )
                        })
                        TextField(
                            value = form.singleWallpaperId.toString(),
                            onValueChange = { form.singleWallpaperId = it },
                            label = {
                                Text(
                                    "Tags (comma-separated)"
                                )
                            })
                    }
                }
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    Button(
                        onClick = {
                            onWallpaperUiEvents(WallpaperUiEvents.DeleteWallpaper(selectedBanner.id))
                            isBannerModDialogVisible.value = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    ) {
                        Text("Delete", color = Color.White)
                    }

                    Button(
                        onClick = {
                            val updatedBanner = selectedBanner.copy(
                                bannerUrl = form.bannerUrl,
                                category = form.category,
                                pageName = form.pageName,
                                openLink = form.openLink,
                                listIds = form.listIds.split(',').mapNotNull { it.toIntOrNull() },
                                singleWallpaperId = form.singleWallpaperId.toInt(),
                            )
                            onWallpaperUiEvents(WallpaperUiEvents.UpsertBanner(updatedBanner))
                            isBannerModDialogVisible.value = false
                        }
                    ) {
                        Text("Update")
                    }

                    Button(
                        onClick = {
                            isBannerModDialogVisible.value = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            }
        )
    }

}