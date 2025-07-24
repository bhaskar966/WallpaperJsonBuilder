package com.beehomie.wallpaperjsonbuilder.presentation.util_ui_components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SynthesizeLinksButton(
    onSynthesizeClick: () -> Unit
){
    Button(
        onClick = { onSynthesizeClick() }
    ){
        Text("Synthesize JSON links")
    }
}