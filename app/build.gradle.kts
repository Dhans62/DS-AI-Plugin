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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // Membaca plugins-api.jar dari folder libs
    compileOnly(fileTree("libs") { include("*.jar") })
}
