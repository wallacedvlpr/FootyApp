package com.example.footyapp.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.Exception

class LiveDataConnection private constructor(context: Context){

    //Variables
    private val liveData = MutableLiveData<Boolean>()
    private var data:Boolean = false
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkCallback: ConnectivityManager.NetworkCallback by lazy {
        connectivityManagerCallback()
    }

    private fun connectivityManagerCallback(): ConnectivityManager.NetworkCallback{
        try {
            return object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                super.onAvailable(network)
                    liveData.postValue(true)
                    data = true
                }
                override fun onUnavailable() {
                super.onUnavailable()
                    //postValue(false)
                    liveData.postValue(false)
                    data = false
                }
                override fun onLost(network: Network) {
                super.onLost(network)
                    //postValue(false)
                    liveData.postValue( false)
                    data = false
                }
            }
        }
        catch (e: Exception)
        {
            Log.e("CheckNetwork", "NetworkCallback object was not created")
            e.printStackTrace()
            throw e
        }
    }

    fun isConnectionLive (): LiveData<Boolean> = liveData
    fun isConnected(): Boolean = data
    fun registerCallBack() {
        try {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        }catch (e: IllegalArgumentException){
            e.printStackTrace()
        }
    }

    fun unregisterCallBack(){
        try {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }catch (e: IllegalArgumentException){
            e.printStackTrace()
        }
    }

    //Single Instance variable
    companion object{
        @Volatile
        private var instance: LiveDataConnection? = null
        fun getInstance(context: Context)  =
            instance ?: synchronized(this) {
                instance
                    ?: LiveDataConnection(context).also {
                    instance = it
                }
            }
    }
}