package com.sina.covid19project.data.data_model


import com.google.gson.annotations.SerializedName

data class WorldData(
    @SerializedName("cases")
    val cases: Int?,
    @SerializedName("casesPerOneMillion")
    val casesPerOneMillion: Int?,
    @SerializedName("critical")
    val critical: Int?,
    @SerializedName("criticalPerOneMillion")
    val criticalPerOneMillion: Double?,
    @SerializedName("deaths")
    val deaths: Int?,
    @SerializedName("deathsPerOneMillion")
    val deathsPerOneMillion: Double?,
    @SerializedName("oneCasePerPeople")
    val oneCasePerPeople: Float?,
    @SerializedName("oneDeathPerPeople")
    val oneDeathPerPeople: Float?,
    @SerializedName("oneTestPerPeople")
    val oneTestPerPeople: Float?,
    @SerializedName("population")
    val population: Long?,
    @SerializedName("recovered")
    val recovered: Int?,
    @SerializedName("tests")
    val tests: Int?,
    @SerializedName("todayCases")
    val todayCases: Int?,
    @SerializedName("todayDeaths")
    val todayDeaths: Int?,
    @SerializedName("todayRecovered")
    val todayRecovered: Int?,
)