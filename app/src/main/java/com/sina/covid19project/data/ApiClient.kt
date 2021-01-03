package com.sina.covid19project.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{
    private const val BASE_URL="https://disease.sh/v3/covid-19/"
    private var retrofit:Retrofit?=null


    val client:Retrofit
        get() {

            if (retrofit == null) {
                retrofit =Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            }
            return retrofit!!
        }
}