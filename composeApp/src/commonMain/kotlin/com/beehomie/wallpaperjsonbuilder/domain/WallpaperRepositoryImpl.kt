package com.beehomie.wallpaperjsonbuilder.domain

import com.beehomie.wallpaperjsonbuilder.domain.models.Banner
import com.beehomie.wallpaperjsonbuilder.local.WallpaperDB
import com.beehomie.wallpaperjsonbuilder.domain.models.Wallpaper
import com.beehomie.wallpaperjsonbuilder.local.entities.BannerEntity
import com.beehomie.wallpaperjsonbuilder.local.entities.WallpaperEntity
import com.beehomie.wallpaperjsonbuilder.mappers.toBanner
import com.beehomie.wallpaperjsonbuilder.mappers.toBannerDto
import com.beehomie.wallpaperjsonbuilder.mappers.toBannerEntity
import com.beehomie.wallpaperjsonbuilder.mappers.toWallpaper
import com.beehomie.wallpaperjsonbuilder.mappers.toWallpaperDto
import com.beehomie.wallpaperjsonbuilder.mappers.toWallpaperEntity
import com.beehomie.wallpaperjsonbuilder.remote.dto.BeeWallsDto
import com.beehomie.wallpaperjsonbuilder.remote.repository.WallpaperRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException

class WallpaperRepositoryImpl(
    private val wallpaperDB: WallpaperDB,
    private val httpClient: HttpClient,
    private val json: Json
): WallpaperRepository {

    private val dao = wallpaperDB.wallpaperDao()

    override suspend fun insertWallpaper(wallpaper: WallpaperEntity) {
        withContext(Dispatchers.IO) {
            dao.insertWallpaper(wallpaper)
        }
    }

    override suspend fun upsertWallpaper(wallpaper: WallpaperEntity) {
        withContext(Dispatchers.IO) {
            dao.upsertWallpaper(wallpaper)
            print("repository: upserted one wallpaper\n")
        }
    }

    override suspend fun updateWallpaper(id: Int) {
        withContext(Dispatchers.IO){
            val wallpaper = dao.findWallpaperById(id)
            dao.updateWallpaper(wallpaper)
        }
    }

    override suspend fun deleteWallpaper(id: Int) {
        withContext(Dispatchers.IO) {
            val wallpaper = dao.findWallpaperById(id)
            dao.deleteWallpaper(wallpaper)
        }
    }

    override suspend fun insertBanner(banner: BannerEntity) {
        withContext(Dispatchers.IO){
            dao.insertBanner(banner)
        }
    }

    override suspend fun upsertBanner(banner: BannerEntity) {
        withContext(Dispatchers.IO){
            dao.upsertBanner(banner)
            print("repository: upserted one banner\n")
        }
    }

    override suspend fun updateBanner(id: Int) {
        withContext(Dispatchers.IO){
            val banner = dao.findBannerById(id)
            dao.updateBanner(banner)
        }
    }

    override suspend fun deleteBanner(id: Int) {
        withContext(Dispatchers.IO){
            val banner = dao.findBannerById(id)
            dao.deleteBanner(banner)
        }
    }

    override suspend fun getAllWallpapers(): Flow<List<Wallpaper>> {
        return flow {
            val wallpaperEntities = dao.getAllWallpapers()
            val wallpapers = wallpaperEntities.map { wallpaperEntity ->
                wallpaperEntity.toWallpaper()
            }
            emit(wallpapers)
            print("repository: emited list of wallpapers\n")
            print("repository: emitting ${wallpapers.size} wallpapers\n")
        }
    }

    override suspend fun getAllBanners(): Flow<List<Banner>> {
        return flow {
            val bannerEntities = dao.getAllBanners()
            val banners = bannerEntities.map { bannerEntity ->
                bannerEntity.toBanner()
            }
            emit(banners)
            print("repository: emitted list of wallpapers\n")
            print("repository: emitting ${banners.size} wallpapers\n")
        }
    }

    override suspend fun getJson(file: File?, link: String?): BeeWallsDto {
        return withContext(Dispatchers.IO) {
            when {
                file != null && link == null -> {
                    try {
                        val jsonText = file.readText()
                        print("repository: parsing json from file\n")
                        json.decodeFromString<BeeWallsDto>(jsonText)
                    } catch (e: NoSuchFileException) {
                        print("No such file: $e")
                        throw e
                    } catch (e: FileNotFoundException) {
                        print("No not found: $e")
                        throw e
                    } catch (e: Exception) {
                        print("exception: $e")
                        throw e
                    }
                }

                link != null && file == null -> {
                    try {
                        val res = httpClient.get(link).bodyAsText()
                        print("repository: parsing json from api\n")
                        print("repository: raw data: $res \n")
                        val jsonRes = json.decodeFromString<BeeWallsDto>(res)
                        print("repository: wallpaper count: $${jsonRes.wallpapers.size}")
                        jsonRes
                    } catch (e: Exception) {
                        print("error while fetching from API : $e")
                        throw e
                    }
                }

                else -> {
                    throw IllegalArgumentException("Either file or link must be provided, but not both or none.")
                }
            }
        }
    }

    override suspend fun loadData(file: File?, link: String?) {
        withContext(Dispatchers.IO){
            val json = when {
                file != null && link == null -> getJson(file = file)
                link != null && file == null -> getJson(link = link)
                else -> throw IllegalArgumentException("Either file or link must be non-null (only one at a time).")
            }
            print("repository: loading data\n")
            json.wallpapers.map { wallpaperDto ->
                wallpaperDto.toWallpaperEntity()
            }.forEach { wallpaperEntity ->
                upsertWallpaper(wallpaperEntity)
            }
            json.banners.map { bannerDto ->
                bannerDto.toBannerEntity()
            }.forEach { bannerEntity ->
                upsertBanner(bannerEntity)
            }
        }


    }

    override suspend fun getAllTags(): List<String> {
        return withContext(Dispatchers.IO){
            dao.getAllWallpaperTags()
                .flatMap { it.split(",") }
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .distinct()
        }
    }

    override suspend fun getAllCategories(): List<String> {
        return withContext(Dispatchers.IO){
            dao.getAllWallpaperCategories()
                .flatMap { it.split(",") }
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .distinct()
        }
    }

    override suspend fun writeLocalFile(path: String) {
        withContext(Dispatchers.IO) {
            try {
                // Create a filename programmatically
                val fileName = "BeeWallsExport_${System.currentTimeMillis()}.json"

                // Create the file using absolute path + filename
                val file = File(path, fileName)

                // Convert data to DTO
                val wallpaperDto = dao.getAllWallpapers().map { it.toWallpaperDto() }
                val bannerDto = dao.getAllBanners().map { it.toBannerDto() }
                val beeWallsDto = BeeWallsDto(wallpapers = wallpaperDto, banners = bannerDto)

                // Encode to JSON string
                val json = json.encodeToString(
                    serializer = BeeWallsDto.serializer(),
                    value = beeWallsDto
                )

                // Write the JSON to the file
                file.writeText(json)

                println("✅ Export successful: ${file.absolutePath}")

            } catch (e: Exception) {
                println("❌ Could not write file: $e")
            }
        }
    }

    override suspend fun clearAllData() {
        withContext(Dispatchers.IO){
            dao.clearAllData()
        }
    }

    override suspend fun sanitizeLinks() {
        withContext(Dispatchers.IO){
            val wallpapers = dao.getAllWallpapers()
            val cleanedWallpapers = wallpapers.map { wallpaperEntity ->
                wallpaperEntity.copy(
                    url = wallpaperEntity.url.substringBefore("?"),
                    thumbnail = wallpaperEntity.thumbnail.substringBefore("?")
                )
            }
            cleanedWallpapers.forEach { wallpaperEntity ->
                dao.upsertWallpaper(wallpaperEntity)
            }
        }
    }

}