package com.sina.covid19project.di

import com.sina.covid19project.repository.HomeRepository
import com.sina.covid19project.data.network.ApiClient
import com.sina.covid19project.data.network.ApiInterface
import com.sina.covid19project.repository.ListRepository
import org.koin.dsl.module


val networkModules= module {
    single { ApiClient.client.create(ApiInterface::class.java) }
    single { HomeRepository(get(),get()) }
    single { ListRepository(get()) }
}