package com.example.footyapp.view.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.footyapp.MainActivity
import com.example.footyapp.R

class SplashScreen : AppCompatActivity() {
    //Lets see
    // This is the loading time of the splash screen
    private val splashTimeOut:Long = 3000 // 3 sec
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        finish()
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