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
    private val connection = MutableLiveData<Boolean>()
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkCallback: ConnectivityManager.NetworkCallback by lazy {
        connectivityManagerCallback()
    }

   /* override fun onActive() {
        try {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        }catch (e: IllegalArgumentException){
            e.printStackTrace()
        }
        super.onActive()
    }

    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        super.onInactive()
    }

    fun close(){
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
    */

    private fun connectivityManagerCallback(): ConnectivityManager.NetworkCallback{
        try {
            return object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                super.onAvailable(network)
                    //postValue(true)
                    connection.postValue(true)
                }
                override fun onUnavailable() {
                super.onUnavailable()
                    //postValue(false)
                    connection.postValue(false)
                }
                override fun onLost(network: Network) {
                super.onLost(network)
                    //postValue(false)
                    connection.postValue( false)
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

//    @Suppress("DEPRECATION")
//    fun updateConnection(){
//        val activeNet: NetworkInfo? =connectivityManager.activeNetworkInfo
//        postValue(activeNet?.isConnected == true)
//    }

   /* fun testConnection(l: LifecycleOwner,
                       tv: TextView,
                       function: () -> Unit ){
        networkConnection?.let {
            it.observe(l, Observer { isConnected ->
                if (!isConnected) {
                    // Disable views
                    tv.visibility = View.VISIBLE
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
                } else {
                    // Enable views
                    tv.visibility = View.INVISIBLE
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 0F)
                    function.invoke()
                }
            })
        }
    }*/

    fun isConnected (): LiveData<Boolean> = connection

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
                    instance!!.registerCallBack()
                }
            }
    }
}