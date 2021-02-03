package com.sina.covid19project.fragments.home

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.covid19project.data.data_model.WorldData
import com.sina.covid19project.fragments.list_country.ListViewModel
import com.sina.covid19project.repository.MyRepository
import com.sina.covid19project.utils_extentions.ListState
import com.sina.covid19project.utils_extentions.Utility
import com.sina.covid19project.utils_extentions.reformat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class HomeViewModel(
    val context: Context,
    private val repository: MyRepository,
    val sharedPref: SharedPreferences
) : ViewModel() {

    companion object {
        const val TAG = "HomeViewModel"
        const val CASES = "cases"
        const val TODAY_CASES = "today_cases"
        const val DEATHS = "deaths"
        const val TODAY_DEATH = "today_deaths"
        const val RECOVERED = "recovered"
        const val TODAY_RECOVERED = "today_recovered"
        const val DATE_CONST = "date"
    }

    private var _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean>
        get() = _isInternetConnected
    private val _listState = MutableLiveData<ListState>()
    val listState: LiveData<ListState>
        get() = _listState
    var worldData: WorldData? = null

    init {
        _isInternetConnected.value = isOnline()
    }
    private fun isOnline(): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        Log.e(TAG, "isOnline: isNetworkActive1: ${connMgr.isDefaultNetworkActive}")
        //add call back when connectivity state changed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connMgr.run {
                registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        _isInternetConnected.postValue(true)
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        _isInternetConnected.postValue(false)
                    }
                })
            }
        } else {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connMgr.registerNetworkCallback(
                request,
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        _isInternetConnected.postValue(true)
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        _isInternetConnected.postValue(false)
                    }
                })
        }

        return connMgr.isDefaultNetworkActive
    }
    fun getWorldData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                worldData = repository.fetchWorldData()
                writeDataToSharedPref(worldData!!)
                _listState.postValue(ListState.SUCCESSFULLY_LOADED)
            } catch (e: Exception) {
                Log.e(ListViewModel.TAG, "getWorldData: loading failed")
                _listState.postValue(ListState.LOADING_FAILED)
            }
        }
    }

    private fun writeDataToSharedPref(worldData: WorldData) {
        sharedPref.edit().apply {
            putInt(CASES, worldData.cases?:0)
            putInt(TODAY_CASES, worldData.todayCases?:0)
            putInt(DEATHS, worldData.deaths?:0)
            putInt(TODAY_DEATH, worldData.todayDeaths?:0)
            putInt(RECOVERED, worldData.recovered?:0)
            putInt(TODAY_RECOVERED, worldData.todayRecovered?:0)
            putString(DATE_CONST, getRightNowShamsiStr())
        }.apply()
    }

    private fun getRightNowShamsiStr(): String {
        return "${Utility.getCurrentShamsidate()} ${Date().time.reformat()}"
    }

}