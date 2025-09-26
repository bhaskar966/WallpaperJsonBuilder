package com.beehomie.wallpaperjsonbuilder.di

import com.beehomie.wallpaperjsonbuilder.local.CreateDatabase
import com.beehomie.wallpaperjsonbuilder.local.WallpaperDB
import com.beehomie.wallpaperjsonbuilder.remote.repository.WallpaperRepository
import com.beehomie.wallpaperjsonbuilder.domain.WallpaperRepositoryImpl
import com.beehomie.wallpaperjsonbuilder.viewModels.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedModule = module {
    single<WallpaperDB> { CreateDatabase(get()).getDB() }
    singleOf(::WallpaperRepositoryImpl) { bind<WallpaperRepository>() }
    single { MainViewModel(get()) }
}

val apiModule = module {

    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            allowSpecialFloatingPointValues = true
            useAlternativeNames = true
            explicitNulls = false
        }
    }
    single {
        HttpClient(CIO) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(get())
            }
        }
    }
}

val dispatcherModule = module {
    single(named("IO")) { Dispatchers.IO }
    single(named("Default")) { Dispatchers.Default }
    single(named("Main")) { Dispatchers.Main }
}