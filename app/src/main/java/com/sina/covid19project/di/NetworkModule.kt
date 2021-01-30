package com.sina.covid19project.di


import com.sina.covid19project.data.network.ApiClient
import com.sina.covid19project.data.network.ApiInterface
import com.sina.covid19project.repository.MyRepository
import org.koin.dsl.module


val networkModules= module {
    single { ApiClient.client.create(ApiInterface::class.java) }
    single { MyRepository(get()) }
}