package com.sina.covid19project.repository

import android.util.Log
import com.sina.covid19project.data.data_model.ResponseData
import com.sina.covid19project.data.network.ApiInterface

class ListRepository(
    private val apiInterface: ApiInterface
) {
    companion object {
        const val TAG = "ListRepository"
    }

    suspend fun fetchList(): MutableList<ResponseData> {
        return apiInterface.getAllCountriesAsync()
    }

//    suspend fun fetchTranslation(): MutableList<ResponseData> {
//        Log.e(TAG, "fetchTranslation: fetch started", )
//        return apiInterfaceTranslate.getFaListCountry()
//    }
      

}

