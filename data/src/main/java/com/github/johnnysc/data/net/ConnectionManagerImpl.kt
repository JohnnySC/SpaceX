package com.github.johnnysc.data.net

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author Asatryan on 19.05.19
 */
class ConnectionManagerImpl(private val context: Context) : ConnectionManager {

    override fun isNetworkAbsent(): Boolean {
        val conMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = conMgr?.activeNetworkInfo
        return netInfo == null
    }
}