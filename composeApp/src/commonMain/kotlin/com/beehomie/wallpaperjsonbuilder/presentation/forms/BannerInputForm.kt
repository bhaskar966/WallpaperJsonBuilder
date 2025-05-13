package com.beehomie.wallpaperjsonbuilder.presentation.forms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.beehomie.wallpaperjsonbuilder.domain.models.Banner
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiEvents
import com.beehomie.wallpaperjsonbuilder.viewModels.components.formStates.BannerFormState


fun resetForm(form: BannerFormState) {
    form.openLink = ""
    form.category = ""
    form.pageName = ""
    form.listIds = ""
    form.bannerUrl = ""
    form.singleWallpaperId = ""
}

@Composable
fun BannerInputForm(
    isFormVisible: MutableState<Boolean> = mutableStateOf(false),
    onInsertButtonClick: (WallpaperUiEvents) -> Unit
) {
    val form = remember { BannerFormState() }

    AnimatedVisibility(
        visible = isFormVisible.value
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
                isFormVisible.value = !isFormVisible.value
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                ) {
                    Button(
                        onClick = {
                            val newBanner = Banner(
                                id = 0,
                                category = form.category,
                                bannerUrl = form.bannerUrl,
                                listIds = form.listIds.split(',').mapNotNull { it.toIntOrNull() },
                                pageName = form.pageName,
                                openLink = form.openLink,
                                singleWallpaperId = form.singleWallpaperId.toInt()
                            )
                            onInsertButtonClick(
                                WallpaperUiEvents.UpsertBanner(
                                    newBanner
                                )
                            )
                            resetForm(form)
                        }
                    ) {
                        Text("Insert")
                    }
                    Button(
                        onClick = {
                            isFormVisible.value = !isFormVisible.value
                            resetForm(form)
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            },
            title = {
                Text(
                    "Insert New Banner",
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
                        .padding(16.dp)
                ) {
                    TextField(
                        value = form.bannerUrl,
                        onValueChange = { form.bannerUrl = it },
                        label = { Text("Url") }
                    )
                    TextField(
                        value = form.category,
                        onValueChange = { form.category = it },
                        label = { Text("Category") }
                    )
                    TextField(
                        value = form.listIds,
                        onValueChange = { form.listIds = it },
                        label = { Text("List ids (comma-separated)") }
                    )
                    TextField(
                        value = form.openLink,
                        onValueChange = { form.openLink = it },
                        label = { Text("Open Link") }
                    )
                    TextField(
                        value = form.pageName,
                        onValueChange = { form.pageName = it },
                        label = { Text("Page name") }
                    )
                    TextField(
                        value = form.singleWallpaperId,
                        onValueChange = { form.singleWallpaperId = it },
                        label = { Text("Single Wallpaper  Id") }
                    )
                }
            }
        )
    }
}