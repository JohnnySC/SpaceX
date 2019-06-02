package com.github.johnnysc.data.net

import android.net.ConnectivityManager

/**
 * @author Asatryan on 19.05.19
 */
class ConnectionManagerImpl(private val connectivityManager: ConnectivityManager?) : ConnectionManager {

    override fun isNetworkAbsent(): Boolean {
        val netInfo = connectivityManager?.activeNetworkInfo
        return netInfo == null || !netInfo.isConnected
    }
}