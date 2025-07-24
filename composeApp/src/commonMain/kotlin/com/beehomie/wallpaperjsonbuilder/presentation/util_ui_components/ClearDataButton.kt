package com.beehomie.wallpaperjsonbuilder.presentation.util_ui_components

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable

@Composable
fun ClearDataButton(
    onClearButtonClick: () -> Unit
){
    Button(
        onClick = { onClearButtonClick() }
    ){
        Text("Clear all data")
    }
}