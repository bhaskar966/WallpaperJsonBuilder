import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization") version "2.1.20"
}

kotlin {
    jvm("desktop")
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

//    linuxX64(){
//        binaries {
//            executable()
//        }
//    }
//
//    mingwX64(){
//        binaries {
//            executable()
//        }
//    }
//
    sourceSets {
        val desktopMain by getting {
            dependencies {
                implementation(libs.slf4j.simple)
            }
        }
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.compose.material3)
            implementation(libs.compose.desktop)
            val composeBom = project.dependencies.platform("androidx.compose:compose-bom:2025.05.00")
            implementation(composeBom)

            //KOIN
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            //Room
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundeled)

            implementation(libs.kotlinx.serialization)

            // Coil
            implementation(libs.coil.compose)
            implementation(libs.coil.compose.network.okhttp)

            // Ktor
            implementation(libs.ktor.client)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.logger)

            // FileKit
            implementation(libs.filekit.core)
            implementation(libs.filekit.dialogs)
            implementation(libs.filekit.dialogs.compose)
            implementation(libs.filekit.coil)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

dependencies {
//    implementation(libs.androidx.ui.android)
    add("kspDesktop", libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}


compose.desktop {
    application {
        mainClass = "com.beehomie.wallpaperjsonbuilder.MainKt"


        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "Json Builder - Frames"
            packageVersion = "1.0.0"
            linux {
                iconFile.set(project.file("resources/jsonbuilder_Icon.png"))
                appRelease = "WallpaperJsonBuilder"
                shortcut = true
                modules("jdk.security.auth")
            }
            windows {
                iconFile.set(project.file("resources/jsonbuilder_icon.ico"))
                shortcut = true
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

