package com.beehomie.wallpaperjsonbuilder.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.beehomie.wallpaperjsonbuilder.domain.models.Banner
import com.beehomie.wallpaperjsonbuilder.domain.models.Wallpaper
import com.beehomie.wallpaperjsonbuilder.mappers.toBannerEntity
import com.beehomie.wallpaperjsonbuilder.mappers.toWallpaperEntity
import com.beehomie.wallpaperjsonbuilder.remote.repository.WallpaperRepository
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiEvents
import com.beehomie.wallpaperjsonbuilder.viewModels.components.WallpaperUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.files.Path
import java.io.File

class MainViewModel(
    private val repository: WallpaperRepository
) {
    private val _wallpaperUiState = MutableStateFlow(WallpaperUiState())
    val wallpaperUiState = _wallpaperUiState.asStateFlow()

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun onEvent(event: WallpaperUiEvents) {
        when (event) {
            is WallpaperUiEvents.DeleteBanner -> {
                scope.launch {
                    deleteBanner(event.id)
                }
            }

            is WallpaperUiEvents.DeleteWallpaper -> {
                scope.launch {
                    deleteWallpaper(event.id)
                }
            }

            is WallpaperUiEvents.UpdateBanner -> {
                scope.launch {
                    updateBanner(event.id)
                }
            }

            is WallpaperUiEvents.UpdateWallpaper -> {
                scope.launch {
                    updateWallpaper(event.id)
                }
            }

            is WallpaperUiEvents.UpsertBanner -> {
                scope.launch {
                    upsertBanner(event.banner)
                }
            }

            is WallpaperUiEvents.UpsertWallpaper -> {
                scope.launch {
                    upsertWallpaper(event.wallpaper)
                }
            }

            is WallpaperUiEvents.RefreshData -> {
                scope.launch {
                    getAllWallpapers()
                }
            }

            is WallpaperUiEvents.OnExportButtonClick -> {
                scope.launch {
                    exportFile(event.path)
                }
            }
        }
    }

    suspend fun sanitizeLinks(){
        withContext(Dispatchers.IO){
            repository.sanitizeLinks()
            getAllWallpapers()
        }
    }

    suspend fun loadData(file: File? = null, link: String? = null) {
        withContext(Dispatchers.IO) {
            clearAllData()
            when {
                file != null && link == null -> repository.loadData(file = file)
                link != null && file == null -> repository.loadData(link = link)
                else -> throw IllegalArgumentException("ViewModel: Either file or link must be non-null (only one at a time).")
            }
            print("viewmodel: loaded data from api\n")
            getAllWallpapers()
            getAllBanners()
            getCategoryAndTagsSuggestion()
            if(wallpaperUiState.value.isDbEmpty){
                _wallpaperUiState.update {
                    it.copy(
                        needsData = true
                    )
                }
            }else {
                _wallpaperUiState.update {
                    it.copy(
                        needsData = false
                    )
                }
            }
        }
    }

    suspend fun checkForData(){
        withContext(Dispatchers.IO){
            getAllWallpapers()
            getAllBanners()
            getCategoryAndTagsSuggestion()
            if(wallpaperUiState.value.isDbEmpty){
                _wallpaperUiState.update {
                    it.copy(
                        needsData = true
                    )
                }
            }else {
                _wallpaperUiState.update {
                    it.copy(
                        needsData = false
                    )
                }
            }
        }
    }

    suspend fun getAllWallpapers() {
        repository.getAllWallpapers().collectLatest { wallpapers ->
            _wallpaperUiState.update {
                it.copy(
                    wallpapers = wallpapers,
                    isDbEmpty = wallpapers.isEmpty()
                )
            }
        }
        print("viewmodel: saved in wallpaper state\n")
        print("viewmodel: is wallpapersList empty= ${wallpaperUiState.value.wallpapers.isEmpty()}\n")
    }

    suspend fun getAllBanners(){
        repository.getAllBanners().collectLatest { banners ->
            if(banners.isEmpty()){
                print("repository returned empty banner list/n")
            }
            _wallpaperUiState.update {
                it.copy(
                    banners = banners,
                    isDbEmpty = banners.isEmpty()
                )
            }
        }

        print("viewmodel: saved banner in state\n")
        print("viewmodel: is banner empty= ${wallpaperUiState.value.wallpapers.isEmpty()}\n")
    }

    private suspend fun upsertWallpaper(wallpaper: Wallpaper) {
        withContext(Dispatchers.IO) {
            repository.upsertWallpaper(wallpaper.toWallpaperEntity())
            getAllWallpapers()
        }
    }

    private suspend fun updateWallpaper(id: Int) {
        withContext(Dispatchers.IO) {
            repository.updateWallpaper(id)
            getAllWallpapers()
        }
    }

    private suspend fun deleteWallpaper(id: Int) {
        withContext(Dispatchers.IO) {
            repository.deleteWallpaper(id)
            getAllWallpapers()
        }
    }

    private suspend fun upsertBanner(banner: Banner) {
        withContext(Dispatchers.IO) {
            repository.upsertBanner(banner.toBannerEntity())
        }
    }

    private suspend fun updateBanner(id: Int) {
        withContext(Dispatchers.IO) {
            repository.updateBanner(id)
        }
    }

    private suspend fun deleteBanner(id: Int) {
        withContext(Dispatchers.IO) {
            repository.deleteBanner(id)
        }
    }

    suspend fun exportFile(path: String){
        withContext(Dispatchers.IO){
            repository.writeLocalFile(path)
        }
    }

    fun clearScope() {
        scope.cancel()
    }

    private suspend fun getCategoryAndTagsSuggestion(){
        withContext(Dispatchers.IO){
            val categories = repository.getAllCategories()
            val tags = repository.getAllTags()
            _wallpaperUiState.update {
                it.copy(
                    existingCategoryList = categories,
                    existingTagList = tags
                )
            }
        }

    }

    suspend fun clearAllData(){
        withContext(Dispatchers.IO){
            repository.clearAllData()
            _wallpaperUiState.update {
                it.copy(
                    wallpapers = emptyList(),
                    banners = emptyList(),
                    isDbEmpty = true,
                    needsData = true
                )
            }
        }
    }
}