package com.beehomie.wallpaperjsonbuilder.presentation.FilePickers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiState

@Composable
fun FilePickerSection(
    wallpaperUiState: WallpaperUiState,
    onFilePickerClick: () -> Unit,
    onLinkSubmitClick: (String) -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp),
        modifier = Modifier
            .fillMaxWidth()
    ){
        FilePickerButton(onFilePickerClick)
        JsonLinkPicker(onLinkSubmitClick)
        if(wallpaperUiState.needsData){
            Text(
                text = "Please Select a data source to continue",
                color = Color.Red
            )
        }
    }
}

@Composable
fun JsonLinkPicker(
    onSubmitClick: (String) -> Unit
){

    var link by remember{ mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ){
        TextField(
            value = link,
            onValueChange = {
                link = it
            }
        )
        Button(
            onClick = { onSubmitClick(link) }
        ){
            Text("Submit")
        }
    }
}