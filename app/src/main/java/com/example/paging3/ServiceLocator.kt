package com.example.paging3

import androidx.annotation.MainThread

object ServiceLocator {


    @set:MainThread
    @get:MainThread
    private var _application: MyApplication? = null

    @MainThread
    fun initWith(application: MyApplication) {
        _application = application
    }

    @get:MainThread
    val myApplication: MyApplication
        get() = checkNotNull(_application) {
            "ServiceLocator must be initialized. " +
                    "Call ServiceLocator.initWith(this) in your Application class."
        }
}