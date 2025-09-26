package com.beehomie.wallpaperjsonbuilder.presentation.forms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.beehomie.wallpaperjsonbuilder.domain.models.Wallpaper
import com.beehomie.wallpaperjsonbuilder.presentation.util_ui_components.ChipInputField
import com.beehomie.wallpaperjsonbuilder.viewModels.components.formStates.WallpaperFormState
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiEvents
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiState


fun resetForm(form: WallpaperFormState) {
    form.name = ""
    form.author = null
    form.url = ""
    form.thumbnail = null
    form.category = null
    form.downloadable = true
    form.dimensions = null
    form.size = null
    form.copyright = null
}


@Composable
fun WallpaperInputForm(
    isFromVisible: MutableState<Boolean> = mutableStateOf(false),
    onInsertButtonClick: (WallpaperUiEvents) -> Unit,
    wallpaperUiState: WallpaperUiState
) {

    val form = remember { WallpaperFormState() }
    val categories = wallpaperUiState.existingCategoryList
    val categoryTextField = remember { mutableStateListOf<String>() }
    val tagTextField = remember { mutableStateListOf<String>() }

    AnimatedVisibility(
        visible = isFromVisible.value
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
                isFromVisible.value = !isFromVisible.value
            },
            buttons = {
                Row {
                    Button(
                        onClick = {

                            form.category = categoryTextField.joinToString(",")

                            val newWall = Wallpaper(
                                id = 0,
                                name = form.name,
                                author = if(form.author.isNullOrBlank()) null else form.author,
                                url = form.url,
                                thumbnail = if(form.thumbnail.isNullOrBlank()) null else form.thumbnail,
                                copyright = if(form.copyright.isNullOrBlank()) null else form.copyright,
                                downloadable = form.downloadable,
                                category = form.category?.split(',')?.map { it.trim() }?.filter { it.isNotEmpty() }?.filter { it.isNotBlank() },
                                size = if(form.size == 0) null else form.size,
                                dimensions = if(form.dimensions.isNullOrBlank()) null else form.dimensions
                            )
                            onInsertButtonClick(
                                WallpaperUiEvents.UpsertWallpaper(
                                    newWall
                                )
                            )
                            resetForm(form)
                            isFromVisible.value = false
                        }
                    ) {
                        Text("Insert")
                    }
                    Button(
                        onClick = {
                            isFromVisible.value = !isFromVisible.value
                            resetForm(form)
                            tagTextField.clear()
                            categoryTextField.clear()
                        }
                    ) {
                        Text("Cancel")
                    }
                }

            },
            title = {
                Text(
                    "Insert New Wallpaper",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    TextField(
                        value = form.name,
                        onValueChange = { form.name = it },
                        label = { Text("Name") }
                    )
                    TextField(
                        value = form.author ?: "",
                        onValueChange = { form.author = it },
                        label = { Text("Author") }
                    )
                    TextField(
                        value = form.url,
                        onValueChange = { form.url = it },
                        label = { Text("Image URL") }
                    )
                    TextField(
                        value = form.thumbnail ?: "",
                        onValueChange = { form.thumbnail = it },
                        label = { Text("Thumbnail URL") }
                    )
                    TextField(
                        value = if(form.size == null) "" else form.size.toString(),
                        onValueChange = { form.size = it.toInt() },
                        label = { Text("Size") }
                    )
                    TextField(
                        value = form.dimensions ?: "",
                        onValueChange = { form.dimensions = it },
                        label = { Text("dimensions") }
                    )
                    TextField(
                        value = form.copyright ?: "",
                        onValueChange = { form.copyright = it },
                        label = { Text("copyright") }
                    )
                    ChipInputField(
                        label = "Categories",
                        suggestions = categories,
                        selectedItems = categoryTextField
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = form.downloadable, onCheckedChange = { form.downloadable = it })
                        Text("Downloadable")
                    }
                }
            }
        )
    }

}
