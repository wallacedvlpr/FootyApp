package com.example.footyapp


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.LiveData
import java.lang.Exception

class NetworkConnection private constructor(private val context: Context): LiveData<Boolean>() {

    //Variables
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        updateConnection()
        connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback())
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback())
    }

    private fun connectivityManagerCallback(): ConnectivityManager.NetworkCallback{
        try {
            networkCallback = object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                super.onAvailable(network)
                postValue(true)
                }
                override fun onUnavailable() {
                super.onUnavailable()
                postValue(false)
                }
                override fun onLost(network: Network) {
                super.onLost(network)
                postValue(false)
                }
            }
            return networkCallback
        }
        catch (e: Exception)
        {
            Log.e("CheckNetwork", "NetworkCallback object was created")
            e.printStackTrace()
            throw e
        }
    }

    @Suppress("DEPRECATION")
    fun updateConnection(){
        val activeNet: NetworkInfo? =connectivityManager.activeNetworkInfo
        postValue(activeNet?.isConnected == true)
    }

    //Static variable
    companion object{
        var networkConnection: NetworkConnection? = null
        fun getInstance(context: Context):  NetworkConnection? {
            if(networkConnection == null) {
                networkConnection = NetworkConnection(context)
            }
            return networkConnection
        }
    }
}