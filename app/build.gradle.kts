plugins {
    id("com.android.library")
}

android {
    namespace = "com.dsai.coding"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    // WAJIB TAMBAHKAN INI: Matikan fitur resource agar file themes.xml lama tidak dibaca
    buildFeatures {
        resValues = false
        androidResources = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // Membaca plugins-api.jar dari folder libs
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}
