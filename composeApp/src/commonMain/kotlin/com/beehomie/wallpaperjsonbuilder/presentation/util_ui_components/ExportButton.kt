package com.beehomie.wallpaperjsonbuilder.presentation.util_ui_components

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable

@Composable
fun ExportButton(
    onExportButtonClick: () -> Unit
){
    Button(
        onClick = { onExportButtonClick() }
    ){
        Text("Export Data")
    }

}