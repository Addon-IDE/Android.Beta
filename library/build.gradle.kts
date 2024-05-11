plugins {
    id("com.android.library")
    id("kotlin-android")
}

group = "android.library"
version = 33

android {
    namespace = "android.library"
    compileSdk = 33
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        
        minSdk = 21
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
    
}

dependencies {
    //Loading Jar
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //androidx
    implementation("androidx.annotation:annotation:1.2.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    //project
    implementation(project(":editor"))
}
