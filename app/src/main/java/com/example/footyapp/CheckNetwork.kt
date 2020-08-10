package com.example.footyapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import java.lang.Exception

class CheckNetwork private constructor(private val context: Context){
    val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val request = NetworkRequest.Builder().build()


    val networkCallback = object : ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            isConnected = true
        }
        override fun onUnavailable() {
            super.onUnavailable()
            isConnected = false
        }
        override fun onLost(network: Network) {
            super.onLost(network)
            isConnected = false
        }
    }
    fun unregisterNetworkCallback(){
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (e: Exception){
            Log.e("CheckNetwork", "NetworkCallback was not registered or already unregistered")
            e.printStackTrace()
        }
    }

    fun registerNetworkCallback(){
        try{
            connectivityManager.registerNetworkCallback(request, networkCallback)
        } catch (e: Exception){
            Log.e("CheckNetwork", "NetworkCallback did not register")
            e.printStackTrace()
        }
    }

    //Static variable
    companion object{
        var isConnected: Boolean = false
        fun newInstance(context: Context):  CheckNetwork = CheckNetwork(context)

    }
}