package com.example.paging3

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.initWith(this)
    }
}