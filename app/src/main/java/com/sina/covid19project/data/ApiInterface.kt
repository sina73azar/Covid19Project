package com.sina.covid19project.data

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiInterface {

    @GET("all")
    fun getMainMessage():Call<JsonObject>
    @GET("countries")
    fun getAllCountries():Call<MutableList<ResponseData>>

}