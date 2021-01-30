package com.sina.covid19project.data.network

import android.util.Log
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{

    private const val BASE_URL="https://rayawin.ir/content/countryapi/"

//    private const val BASE_URL="https://disease.sh/v3/covid-19/"
    private var retrofit:Retrofit?=null


    private val interceptor = LoggingInterceptor.Builder()
        .setLevel(Level.BASIC)
        .log(Log.VERBOSE)
        .build()
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val client:Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build()

            }

            return retrofit!!
        }
}