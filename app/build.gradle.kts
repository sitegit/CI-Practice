plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    // Применяем плагины:
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.appdistribution)
}

android {
    namespace = "com.divo.practice"

    compileSdk = 36
    defaultConfig {
        applicationId = "com.divo.practice"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    // --- ДОБАВЛЕН БЛОК ПОДПИСИ КАК В ПРОЕКТЕ TELEGRAM ---
    signingConfigs {
        getByName("debug") {
            val storeFilePath = findProperty("DEBUG_STORE_FILE") as? String ?: System.getenv("DEBUG_STORE_FILE")
            if (storeFilePath != null) {
                storeFile = file(storeFilePath)
                storePassword = findProperty("DEBUG_STORE_PASSWORD") as? String ?: System.getenv("DEBUG_STORE_PASSWORD")
                keyAlias = findProperty("DEBUG_KEY_ALIAS") as? String ?: System.getenv("DEBUG_KEY_ALIAS")
                keyPassword = findProperty("DEBUG_KEY_PASSWORD") as? String ?: System.getenv("DEBUG_KEY_PASSWORD")
            }
        }
        create("release") {
            val storeFilePath = findProperty("RELEASE_STORE_FILE") as? String ?: System.getenv("RELEASE_STORE_FILE")
            if (storeFilePath != null) {
                storeFile = file(storeFilePath)
                storePassword = findProperty("RELEASE_STORE_PASSWORD") as? String ?: System.getenv("RELEASE_STORE_PASSWORD")
                keyAlias = findProperty("RELEASE_KEY_ALIAS") as? String ?: System.getenv("RELEASE_KEY_ALIAS")
                keyPassword = findProperty("RELEASE_KEY_PASSWORD") as? String ?: System.getenv("RELEASE_KEY_PASSWORD")
            }
        }
    }
    buildTypes {
        getByName("debug") {
            // Привязываем дебаг конфиг
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Привязываем релизный конфиг
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // ПОДКЛЮЧАЕМ FIREBASE:
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics) // Версия не нужна, она берется из BoM
}