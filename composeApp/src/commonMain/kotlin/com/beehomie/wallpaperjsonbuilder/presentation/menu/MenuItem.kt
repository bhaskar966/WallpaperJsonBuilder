package com.beehomie.wallpaperjsonbuilder.presentation.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MenuItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) ButtonDefaults.buttonColors().containerColor else ButtonDefaults.buttonColors().disabledContainerColor
    val textColor = if (isSelected) ButtonDefaults.buttonColors().contentColor else MaterialTheme.colors.onBackground
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clip(ButtonDefaults.shape)
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Text(
            text = label,
            color = textColor
            )
    }
}
