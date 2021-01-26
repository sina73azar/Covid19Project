package com.sina.covid19project.di

import android.content.Context
import android.content.SharedPreferences
import com.sina.covid19project.fragments.home.HomeViewModel
import com.sina.covid19project.fragments.list_country.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
const val NAME_SHARED_PREF = "CovidEarthData"
val viewModelModule = module {

    viewModel { HomeViewModel(get(), get()) }
    single { createSharedPreferences(get())}
    single { ListViewModel(get(),get()) }
}

fun createSharedPreferences(context: Context):SharedPreferences =
    context.getSharedPreferences(NAME_SHARED_PREF,Context.MODE_PRIVATE)

