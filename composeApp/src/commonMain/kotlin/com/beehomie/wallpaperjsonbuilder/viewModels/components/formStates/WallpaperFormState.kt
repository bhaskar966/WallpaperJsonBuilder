package com.beehomie.wallpaperjsonbuilder.viewModels.components.formStates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class WallpaperFormState {
    var name by mutableStateOf("")
    var author by mutableStateOf<String?>(null)
    var url by mutableStateOf("")
    var thumbnail by mutableStateOf<String?>(null)
    var downloadable by mutableStateOf(true)

    var category by mutableStateOf<String?>(null)
    var size by mutableStateOf<Int?>(null)
    var dimensions by mutableStateOf<String?>(null)
    var copyright by mutableStateOf<String?>(null)

}