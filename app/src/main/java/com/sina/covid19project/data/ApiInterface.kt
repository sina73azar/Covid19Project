package com.sina.covid19project.data

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface ApiInterface {

    @GET("all")
    fun getMainMessage():Call<JsonObject>
    @GET("countries")
    fun getAllCountries():Call<MutableList<ResponseData>>
    @GET("countries/{country}")
    fun getSpecificCountry(@Path("country")countryName:String):Call<ResponseCountry>

}