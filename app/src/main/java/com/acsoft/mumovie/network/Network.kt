package com.acsoft.mumovie.network

import android.content.Context
import android.net.ConnectivityManager




class Network {

    companion object {
        fun isNetworkAvailable(context: Context):Boolean{
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }
    }
}