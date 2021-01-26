package com.sina.covid19project.data.network

import com.google.gson.JsonObject

import com.sina.covid19project.data.data_model.ResponseData

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("all")
    fun getHomeData():Call<JsonObject>
    @GET("countries")
    suspend fun getAllCountriesAsync():MutableList<ResponseData>

//    @GET("countries/{country}")
//    fun getSpecificCountry(@Path("country")countryName:String):Call<ResponseCountry>

}