package com.sina.covid19project.repository


import com.sina.covid19project.data.data_model.ResponseData
import com.sina.covid19project.data.data_model.WorldData
import com.sina.covid19project.data.network.ApiInterface

class MyRepository(private val apiInterface: ApiInterface) {
    suspend fun fetchList(): MutableList<ResponseData> {
        return apiInterface.getAllCountriesAsync()
    }
    suspend fun fetchWorldData(): WorldData {
        return apiInterface.getWorldData()
    }
}

