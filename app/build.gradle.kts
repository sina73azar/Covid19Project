plugins {

    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.hilt.android)
    kotlin("plugin.serialization") version "1.9.0"
    kotlin("kapt")

    id("androidx.navigation.safeargs.kotlin") // for pure kotlin
    id("androidx.navigation.safeargs") // for java or mixed java and kotlin
}

android {
    namespace = "com.sina.covid19project"
    compileSdk = 34
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.sina.covid19project"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
        dataBinding = true
    }
}

dependencies {
    val koinVersion = "4.1.0-Beta1"
    val coroutinesVersion = "1.3.7"
    val lifecycleVersion = "2.2.0"
    val lottieVersion = "3.4.0"

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.2")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Gson
    implementation("com.google.code.gson:gson:2.8.6")

    // Glide
    implementation("com.github.bumptech.glide:glide:3.7.0")

    // Logging
    implementation("com.github.ihsanbal:LoggingInterceptor:3.1.0") {
        exclude(group = "org.json", module = "json")
    }

    // MVVM
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    // Koin BOM
    implementation(platform("io.insert-koin:koin-bom:$koinVersion"))
    implementation("io.insert-koin:koin-core")
    implementation("io.insert-koin:koin-android")
    implementation("io.insert-koin:koin-androidx-workmanager")
    implementation("io.insert-koin:koin-androidx-navigation")
    implementation("io.insert-koin:koin-androidx-compose")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // Lottie Animation
    implementation("com.airbnb.android:lottie:$lottieVersion")


    /**
     * di
     * */
    implementation(libs.bundles.hilt)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    kapt(libs.hilt.compiler)

}
