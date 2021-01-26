package com.sina.covid19project.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.sina.covid19project.data.network.ApiInterface
import com.sina.covid19project.fragments.home.HomeViewModel
import com.sina.covid19project.utils_extentions.reformat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeRepository(private val apiInterface: ApiInterface, private val sharedPref: SharedPreferences) {
    val dataHomeMap= mutableMapOf<String,String>()
    companion object {
        private const val TAG = "HomeRepository"
         const val CASES = "cases"
         const val TODAY_CASES = "today_cases"
         const val DEATHS= "deaths"
         const val TODAY_DEATH = "today_deaths"
         const val RECOVERED = "recovered"
         const val TODAY_RECOVERED = "today_recovered"
         const val DATE_CONST = "date"
    }
    private var _dataIsLoaded= MutableLiveData<Boolean>()
    val dataIsLoaded: LiveData<Boolean>
        get() = _dataIsLoaded
    fun retrofitRequestForData() {
        Log.e(TAG, "retrofitRequestForData: ")
        val call = apiInterface.getHomeData()
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.e(TAG, "onResponse: $it ")
                        writeDataToMapForViewModelAccess(it)
                        writeDataToSharedPref(it)
                    }
                    //update last updated date to shared pref with current time
                    saveLastUpdateTime()
                    _dataIsLoaded.postValue(true)
                    Log.e(HomeViewModel.TAG, "onResponse: dataIsLoaded is:${_dataIsLoaded.value}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                setMapFromSharedPref()
                _dataIsLoaded.postValue(false)
            }
        })
    }

     fun setMapFromSharedPref() {
        dataHomeMap[CASES]= sharedPref.getString(CASES,"....").toString()
        dataHomeMap[TODAY_CASES]= sharedPref.getString(TODAY_CASES,"...").toString()
        dataHomeMap[DEATHS]= sharedPref.getString(DEATHS,"...").toString()
        dataHomeMap[TODAY_DEATH]= sharedPref.getString(TODAY_DEATH,"...").toString()
        dataHomeMap[RECOVERED]= sharedPref.getString(RECOVERED,"...").toString()
        dataHomeMap[TODAY_RECOVERED] = sharedPref.getString(TODAY_RECOVERED, "...").toString()
        dataHomeMap[DATE_CONST]=sharedPref.getString(DATE_CONST,"...").toString()
    }

    private fun writeDataToMapForViewModelAccess(it: JsonObject) {
        dataHomeMap[CASES]= it["cases"].asString
        dataHomeMap[TODAY_CASES]= it["todayCases"].asString
        dataHomeMap[DEATHS]= it["deaths"].asString
        dataHomeMap[TODAY_DEATH]= it["todayDeaths"].asString
        dataHomeMap[RECOVERED]= it["recovered"].asString
        dataHomeMap[TODAY_RECOVERED]= it["todayRecovered"].asString
    }

    private fun saveLastUpdateTime() {
        val nowStr=getRightNowStr()
        dataHomeMap[DATE_CONST]=nowStr
        sharedPref.edit().putString("date", nowStr).apply()
    }
    private fun getRightNowStr(): String {
        return Date().time.reformat()
    }

    private fun writeDataToSharedPref(it: JsonObject) {
        sharedPref.edit().apply {
            putString(CASES, it["cases"].asString)
            putString(TODAY_CASES, it["todayCases"].asString)
            putString(DEATHS, it["deaths"].asString)
            putString(TODAY_DEATH, it["todayDeaths"].asString)
            putString(RECOVERED, it["recovered"].asString)
            putString(TODAY_RECOVERED, it["todayRecovered"].asString)
        }.apply()
    }
}
