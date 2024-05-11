//插件
plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    //软件包名
    namespace = "com.mcyizy.addonide"
    //构建工具SDK
    compileSdk = 33
    //构建工具版本
    buildToolsVersion = "34.0.4"
    //配置
    defaultConfig {
        applicationId = "com.mcyizy.addonide"
        minSdk = 22
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        
        vectorDrawables { 
            useSupportLibrary = true
        }
    }
    
    //编译选项
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    //构建类型
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    
    //构建功能
    buildFeatures {
        viewBinding = true
     }
    
    
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "17"
}

//依赖
dependencies {
    //本地
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("**/*.jar","**/*.aar"))))
    //androidx
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.0.0")
    //material
    implementation("com.google.android.material:material:1.9.0")
    //其他模块
    implementation("com.obsez.android.lib.filechooser:filechooser:1.2.0")
    
    //加载模块
    implementation(project(":editor"))
    implementation(project(":library"))
}
