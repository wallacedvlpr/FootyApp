package com.example.footyapp.utils

import android.app.Application
import android.content.Context

class CustomApp: Application() {
    override fun onCreate() {
        super.onCreate()
        footyContext = applicationContext
    }
    companion object{
        var footyContext: Context? = null
    }
}