package com.sina.covid19project.fragments.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sina.covid19project.repository.HomeRepository


class HomeViewModel(val context: Context,val repository: HomeRepository) : ViewModel() {

    companion object {
        const val TAG = "HomeViewModel"
    }
    private var _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean>
        get() = _isInternetConnected

    init {
        _isInternetConnected.value=isOnline()
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

    fun retrofitRequestForData() {
        repository.retrofitRequestForData()
    }


}