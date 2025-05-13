package com.beehomie.wallpaperjsonbuilder.viewModels.components.formStates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class WallpaperFormState {
    var name by mutableStateOf("")
    var author by mutableStateOf("")
    var url by mutableStateOf("")
    var thumbnail by mutableStateOf("")
    var isPremium by mutableStateOf(false)
    var downloadable by mutableStateOf(true)

    var category by mutableStateOf("")
    var tags by mutableStateOf("")
}