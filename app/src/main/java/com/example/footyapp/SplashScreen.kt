package com.example.footyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {

    // This is the loading time of the splash screen
    private val splashTimeOut:Long = 3000 // 1 sec
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        if(isFinishing){
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

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
}