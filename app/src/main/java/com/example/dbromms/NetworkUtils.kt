package com.example.dbromms

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object NetworkUtils {
//method
    fun isNetworkAvailable(context: Context) : Boolean {
        // variable for the connectivity manager
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = connectivityManager.activeNetworkInfo
    return activeNetwork!= null &&  activeNetwork.isConnected
    }
}
