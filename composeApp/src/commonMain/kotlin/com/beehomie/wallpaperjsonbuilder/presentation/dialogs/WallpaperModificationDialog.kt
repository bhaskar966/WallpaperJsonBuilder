@file:OptIn(ExperimentalComposeUiApi::class)

package com.beehomie.wallpaperjsonbuilder.presentation.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.beehomie.wallpaperjsonbuilder.domain.models.Wallpaper
import com.beehomie.wallpaperjsonbuilder.presentation.util_ui_components.ChipInputField
import com.beehomie.wallpaperjsonbuilder.viewModels.components.formStates.WallpaperFormState
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiEvents
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiState


@Composable
fun WallpaperModificationDialog(
    selectedWallpaper: Wallpaper,
    isWallpaperWallpaperModDialogVisible: MutableState<Boolean>,
    onWallpaperUiEvents: (WallpaperUiEvents) -> Unit,
    wallpaperUiState: WallpaperUiState
) {
    val form = remember { WallpaperFormState() }
    val categories = wallpaperUiState.existingCategoryList
    val categoryTextField = remember { mutableStateListOf<String>() }
    val tagTextField = remember { mutableStateListOf<String>() }

    // Populate form fields initially
    LaunchedEffect(selectedWallpaper) {
        form.name = selectedWallpaper.name
        form.author = selectedWallpaper.author
        form.url = selectedWallpaper.url
        form.thumbnail = selectedWallpaper.thumbnail
        form.category = categoryTextField.joinToString(",")
        form.downloadable = selectedWallpaper.downloadable ?: true

        categoryTextField.clear()
        selectedWallpaper.category?.let {
            categoryTextField.addAll(it)
        }

        form.category = selectedWallpaper.category?.joinToString(",")

    }

    AnimatedVisibility(visible = isWallpaperWallpaperModDialogVisible.value) {
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
                isWallpaperWallpaperModDialogVisible.value = false
            },
            title = {
                Text(
                    "Edit Wallpaper",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // Left: Image Preview
                    AsyncImage(
                        model = form.thumbnail?.ifEmpty { form.url },
                        contentDescription = "Wallpaper Preview",
                        modifier = Modifier
                            .weight(1f)
                            .height(290.dp)
                            .aspectRatio(9f / 16f)
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
                        Text("id: ${selectedWallpaper.id}")
                        TextField(value = form.name, onValueChange = { form.name = it }, label = { Text("Name") })
                        TextField(value = form.author ?: "", onValueChange = { form.author = it }, label = { Text("Author") })
                        TextField(value = form.url, onValueChange = { form.url = it }, label = { Text("Image URL") })
                        TextField(
                            value = form.thumbnail ?: "",
                            onValueChange = { form.thumbnail = it },
                            label = { Text("Thumbnail URL") })
                        ChipInputField(
                            label = "Categories",
                            suggestions = categories,
                            selectedItems = categoryTextField
                        )

                        TextField(
                            value = if(form.size == null) "" else form.size.toString(),
                            onValueChange = { form.size = it.toInt() },
                            label = { Text("Size") }
                        )
                        TextField(
                            value = form.dimensions ?: "",
                            onValueChange = { form.dimensions = it },
                            label = { Text("Dimensions") }
                        )
                        TextField(
                            value = form.copyright ?: "",
                            onValueChange = { form.copyright = it },
                            label = { Text("Copyright") }
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = form.downloadable, onCheckedChange = { form.downloadable = it })
                            Text("Downloadable")
                        }
                    }
                }
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            onWallpaperUiEvents(WallpaperUiEvents.DeleteWallpaper(selectedWallpaper.id))
                            isWallpaperWallpaperModDialogVisible.value = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    ) {
                        Text("Delete", color = Color.White)
                    }

                    Button(
                        onClick = {

                            form.category = categoryTextField.joinToString(",")
                            val categoryList = form.category?.split(',')?.map { it.trim() }?.filter { it.isNotBlank() }?.filter { it.isNotEmpty() }

                            val updatedWall = selectedWallpaper.copy(
                                id = selectedWallpaper.id,
                                name = form.name,
                                author = if(form.author.isNullOrBlank()) null else form.author,
                                url = form.url,
                                thumbnail = if(form.thumbnail.isNullOrBlank()) null else form.thumbnail,
                                copyright = if(form.copyright.isNullOrBlank()) null else form.copyright,
                                downloadable = form.downloadable,
                                category = if(categoryList.isNullOrEmpty()) null else categoryList,
                                size = if(form.size == 0) null else form.size,
                                dimensions = if(form.dimensions.isNullOrBlank()) null else form.dimensions
                            )
                            onWallpaperUiEvents(WallpaperUiEvents.UpsertWallpaper(updatedWall))
                            isWallpaperWallpaperModDialogVisible.value = false
                        }
                    ) {
                        Text("Update")
                    }

                    Button(
                        onClick = {
                            isWallpaperWallpaperModDialogVisible.value = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            }
        )
    }
}
