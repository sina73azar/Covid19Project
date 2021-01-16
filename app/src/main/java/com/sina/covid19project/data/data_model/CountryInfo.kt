package com.sina.covid19project.data.data_model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class CountryInfo(
    @SerializedName("_id")
    @Expose
    val id: Int?,
    @SerializedName("iso2")
    @Expose
    val iso2: String?,
    @SerializedName("iso3")
    @Expose
    val iso3: String?,
    @SerializedName("lat")
    @Expose
    val lat: Double?,
    @SerializedName("long")
    @Expose
    val _long: Double?,
    @SerializedName("flag")
    @Expose
    val flag: String?
)