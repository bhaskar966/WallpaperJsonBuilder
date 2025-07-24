package com.beehomie.wallpaperjsonbuilder.presentation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.beehomie.wallpaperjsonbuilder.presentation.util_ui_components.ClearDataButton
import com.beehomie.wallpaperjsonbuilder.presentation.util_ui_components.ExportButton
import com.beehomie.wallpaperjsonbuilder.presentation.util_ui_components.SynthesizeLinksButton

@Composable
fun MoreOptions(
    clearButtonClick: () -> Unit,
    exportButtonClick: () -> Unit,
    onSynthesizeClick: () -> Unit,
){
    Box(){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ClearDataButton(clearButtonClick)
            ExportButton(exportButtonClick)
            SynthesizeLinksButton(onSynthesizeClick)
        }
    }
}