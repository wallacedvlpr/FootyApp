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
}