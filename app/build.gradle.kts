import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
}

android {
    namespace = "com.example.paging3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.paging3"
        minSdk = 32
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        debug {
//            val properties = Properties().apply {
//                load(rootProject.file("local.properties").inputStream())
//            }
//
//            buildConfigField(
//                type = "String",
//                name = "UNSPLASH_CLIENT_ID",
//                value = "\"" + properties["UNSPLASH_CLIENT_ID"]!!.toString() + "\"",
//            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.fragment.ktx)
    implementation(libs.activity.ktx)
    implementation(libs.livedata.ktx)
    implementation(libs.viewmodel.ktx)

    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // Moshi
    val moshiVersion = "1.15.1"
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")

    // OkHttp
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.11.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    // Retrofit
    val retrofitVersion = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    // Glide
    val glideVersion = "4.14.2"
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    implementation("com.github.bumptech.glide:okhttp3-integration:$glideVersion")
    // Skip this if you don't want to use integration libraries or configure Glide.
    kapt("com.github.bumptech.glide:compiler:$glideVersion")

    implementation("androidx.paging:paging-common-android:3.3.0")
    implementation("androidx.paging:paging-runtime-ktx:3.3.0")
}