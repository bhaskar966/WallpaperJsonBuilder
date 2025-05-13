package com.beehomie.wallpaperjsonbuilder.viewModels.components.formStates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class BannerFormState {

    var bannerUrl by mutableStateOf("")
    var category by mutableStateOf("")
    var listIds by mutableStateOf("")
    var pageName by mutableStateOf("")
    var openLink by mutableStateOf("")
    var singleWallpaperId by mutableStateOf("")

}