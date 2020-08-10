package com.example.footyapp.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import com.example.footyapp.MainActivity
import com.example.footyapp.R
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class SplashScreen : AppCompatActivity() {
    //Lets see
    // This is the loading time of the splash screen
    private val splashTimeOut:Long = 3000 // 1 sec
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        if(isFinishing){
            startActivity(Intent(applicationContext, MainActivity::class.java))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        CheckNetwork(this).registerNetworkCallback()
        if (CheckNetwork.isConnected){
            tv_no_connection.visibility = TextView.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        //Splash Screen to Show for 3 seconds
        handler.postDelayed(runnable, splashTimeOut)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    class CheckNetwork(private val context: Context){
        val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()
        val networkCallback = object :ConnectivityManager.NetworkCallback(){
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
        }
    }
}