// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("androidx.navigation.safeargs") version "2.5.3" apply false
}
/*
buildscript {
    ext.kotlin_version = "1.4.21"
    ext {
        compose_version = '1.1.1'
    }
    ext {
        koin_version = "4.1.0-Beta1"
    }
    repositories {
        google()
        jcenter()

    }
*/
/*    dependencies {
        classpath 'com.android.tools.build:gradle:7.1.3'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        def nav_version = "2.3.2"
        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"

        // NOTE: Do not place your application dependencies here; they belong
   *//*
*/
/*     def koin_version = '2.2.2'
        classpath "org.koin:koin-gradle-plugin:$koin_version"*//*
*/
/*
    }*//*

}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://www.jitpack.io' }
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}*/
