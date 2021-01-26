package com.sina.covid19project.application

import android.app.Application
import com.sina.covid19project.di.networkModules
import com.sina.covid19project.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CovidApplication(): Application() {

        override fun onCreate() {
            super.onCreate()
            startMyKoin()
        }

    private fun startMyKoin() {
        startKoin {
            androidContext(this@CovidApplication)
            androidLogger()
            modules(networkModules, viewModelModule)
        }
    }

}