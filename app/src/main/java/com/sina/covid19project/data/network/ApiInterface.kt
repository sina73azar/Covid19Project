package com.sina.covid19project.data.network

import com.google.gson.JsonObject

import com.sina.covid19project.data.data_model.ResponseData
import com.sina.covid19project.data.data_model.WorldData

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("all")
    suspend fun getWorldData():WorldData
    @GET("countries")
    suspend fun getAllCountriesAsync():MutableList<ResponseData>



}