package com.example.searchimagesplash

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.initWith(this)
    }
}