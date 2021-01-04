package com.sina.covid19project.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("updated")
    @Expose
    val updated: Long,
    @SerializedName("country")
    @Expose
    val country: String,
    @SerializedName("countryInfo")
    @Expose
    val countryInfo: CountryInfo,
    @SerializedName("cases")
    @Expose
    val cases: Int,
    @SerializedName("todayCases")
    @Expose
    val todayCases: Int,
    @SerializedName("deaths")
    @Expose
    val deaths: Int,
    @SerializedName("todayDeaths")
    @Expose
    val todayDeaths: Int,
    @SerializedName("recovered")
    @Expose
    val recovered: Int,
    @SerializedName("todayRecovered")
    @Expose
    val todayRecovered: Int,
    @SerializedName("active")
    @Expose
    val active: Int,
    @SerializedName("critical")
    @Expose
    val critical: Int,
    @SerializedName("casesPerOneMillion")
    @Expose
    val casesPerOneMillion: Float,
    @SerializedName("deathsPerOneMillion")
    @Expose
    val deathsPerOneMillion: Float,
    @SerializedName("tests")
    @Expose
    val tests: Int,
    @SerializedName("testsPerOneMillion")
    @Expose
    val testsPerOneMillion: Float,
    @SerializedName("population")
    @Expose
    val population: Int,
    @SerializedName("continent")
    @Expose
    val continent: String,
    @SerializedName("oneCasePerPeople")
    @Expose
    val oneCasePerPeople: Float,
    @SerializedName("oneDeathPerPeople")
    @Expose
    val oneDeathPerPeople: Float,
    @SerializedName("oneTestPerPeople")
    @Expose
    val oneTestPerPeople: Float,
    @SerializedName("activePerOneMillion")
    @Expose
    val activePerOneMillion: Double,
    @SerializedName("recoveredPerOneMillion")
    @Expose
    val recoveredPerOneMillion: Double,
    @SerializedName("criticalPerOneMillion")
    @Expose
    val criticalPerOneMillion: Double,
    var percentage:Float?
)