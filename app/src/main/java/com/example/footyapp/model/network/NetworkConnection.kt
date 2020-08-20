package com.example.footyapp.model.network


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.lang.Exception

class NetworkConnection private constructor(private val context: Context): LiveData<Boolean>() {

    //Variables
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        //updateConnection()
        connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback())
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback())
    }

    fun close(){
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
            Log.e("CheckNetwork", "NetworkCallback object was not created")
            e.printStackTrace()
            throw e
        }
    }

//    @Suppress("DEPRECATION")
//    fun updateConnection(){
//        val activeNet: NetworkInfo? =connectivityManager.activeNetworkInfo
//        postValue(activeNet?.isConnected == true)
//    }

    fun testConnection(l: LifecycleOwner,
                       tv: TextView,
                       function: () -> Unit ){
        networkConnection?.let {
            it.observe(l, Observer { isConnected ->
                if (!isConnected) {
                    // Disable views
                    tv.visibility = View.VISIBLE
                } else {
                    // Enable views
                    tv.visibility = View.GONE
                    function.invoke()
                }
            })
        }
    }
    //Static variable
    companion object{
        var networkConnection: NetworkConnection? = null
        fun getInstance(context: Context):  NetworkConnection? {
            if(networkConnection == null) {
                networkConnection =
                    NetworkConnection(context)
            }
            return networkConnection
        }
        fun unRegister(){
            networkConnection?.close()
        }
    }
}