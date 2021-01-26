package com.sina.covid19project.utils_extentions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class Receiver(val listener:(Boolean)->Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        listener(isConnected(context))
    }

    private fun isConnected(context: Context?): Boolean {
        val mConnectivityManager=context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return mConnectivityManager.isDefaultNetworkActive
    }

}
